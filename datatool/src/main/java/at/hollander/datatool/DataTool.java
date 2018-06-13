package at.hollander.datatool;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import de.micromata.opengis.kml.v_2_2_0.SimpleData;

public class DataTool {

    public static class Challenge {
        public String title;
        public String description;
        public Date startTime;
        public Date endTime;
        public String image;
        public List<Region> regions;

        @Override
        public String toString() {
            return "Challenge{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", image='" + image + '\'' +
                    ", regions=" + regions +
                    '}';
        }
    }

    public static class Point {
        public double latitude;
        public double longitude;

        @Override
        public String toString() {
            return "Point{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

    public static class PointOfInterest {
        public String name;
        public GeoPoint location;
        public double radius;

        @Override
        public String toString() {
            return "PointOfInterest{" +
                    "name='" + name + '\'' +
                    ", location=" + location +
                    ", radius=" + radius +
                    '}';
        }
    }

    public static class Region {
        public String description;
        public List<Point> region;

        @Override
        public String toString() {
            return "Region{" +
                    "description='" + description + '\'' +
                    ", region=" + region +
                    '}';
        }
    }

    private static void setupFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://photosofinterest-518c3.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);


    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        setupFirebase();

        Firestore fs = FirestoreClient.getFirestore();

//        Challenge challenge = fs.document("challenges/BK99EjGsYIyGK44KqJ3V").get().get().toObject(Challenge.class);
//        System.out.println(challenge);

        CollectionReference cr = fs.collection("challenges/BK99EjGsYIyGK44KqJ3V/pois");

        Kml kml = Kml.unmarshal(new File("data/seen.kml"));
        Folder folder = (Folder) (((Document) kml.getFeature()).getFeature()).get(0);

        Kml out = KmlFactory.createKml();
        Folder outf = out.createAndSetFolder();

        JSONArray outArr = new JSONArray();

        for (Feature f : folder.getFeature()) {
            if (f instanceof Placemark) {
                Placemark p = (Placemark) f;
                Polygon geom = (Polygon) p.getGeometry();
                List<Coordinate> coords = geom.getOuterBoundaryIs().getLinearRing().getCoordinates();

                String name = getExtendedData(p, "name");
                String entstehung = getExtendedData(p, "entstehung");
                if (name == null || entstehung == null) continue;

                if (!name.equals("k.A.")) {
                    Coordinate center = compute2DPolygonCentroid(coords);
                    double radius = circleAroundPolygon(center, coords);

                    Placemark placemark = KmlFactory.createPlacemark();
                    placemark.withName(name)
                            .withVisibility(true)
                            .withOpen(false)
                            .createAndSetPoint()
                            .withExtrude(false)
                            .withAltitudeMode(AltitudeMode.CLAMP_TO_GROUND)
                            .addToCoordinates(center.getLongitude(), center.getLatitude());
                    outf.addToFeature(placemark);
                    JSONObject o = new JSONObject();
                    o.put("name", name);
                    o.put("radius", radius);
                    JSONObject c = new JSONObject();
                    c.put("longitude", center.getLongitude());
                    c.put("latitude", center.getLatitude());
                    o.put("coordinates", c);
                    outArr.put(o);

                    PointOfInterest poi = new PointOfInterest();
                    poi.location = new GeoPoint(center.getLatitude(), center.getLongitude());
                    poi.name = name;
                    poi.radius = radius;
                    cr.add(poi).get();
                }
            }
        }

        outArr.write(new FileWriter(new File("data/points.json")));
        out.marshal(new File("data/out.kml"));
    }

    private static double circleAroundPolygon(Coordinate center, List<Coordinate> coords) {
        double radius = 0;
        for (Coordinate c : coords) {
            radius = Math.max(haversine(center, c), radius);
        }
        return radius;
    }

    private static double haversine(Coordinate c1, Coordinate c2) {
        return haversine(c1.getLatitude(), c2.getLatitude(), c1.getLongitude(), c2.getLongitude());
    }

    private static double haversine(double lat1, double lat2, double lon1, double lon2) {
        double R = 6371e3; // metres
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaphi = Math.toRadians(lat2 - lat1);
        double deltalambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltalambda / 2) * Math.sin(deltalambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private static String getExtendedData(Feature f, String key) {
        for (SimpleData sd : f.getExtendedData().getSchemaData().get(0).getSimpleData()) {
            if (sd.getName().equals(key)) {
                return sd.getValue();
            }
        }
        return null;
    }

    /**
     * https://stackoverflow.com/a/2792459/3202650
     */
    private static Coordinate compute2DPolygonCentroid(List<Coordinate> vertices) {
        Coordinate centroid = new Coordinate(0, 0);
        double signedArea = 0.0;
        double x0 = 0.0; // Current vertex X
        double y0 = 0.0; // Current vertex Y
        double x1 = 0.0; // Next vertex X
        double y1 = 0.0; // Next vertex Y
        double a = 0.0;  // Partial signed area

        // For all vertices except last
        int i = 0;
        for (i = 0; i < vertices.size() - 1; ++i) {
            x0 = vertices.get(i).getLongitude();
            y0 = vertices.get(i).getLatitude();
            x1 = vertices.get(i + 1).getLongitude();
            y1 = vertices.get(i + 1).getLatitude();
            a = x0 * y1 - x1 * y0;
            signedArea += a;
            centroid.setLongitude(centroid.getLongitude() + (x0 + x1) * a);
            centroid.setLatitude(centroid.getLatitude() + (y0 + y1) * a);
        }

        // Do last vertex separately to avoid performing an expensive
        // modulus operation in each iteration.
        x0 = vertices.get(i).getLongitude();
        y0 = vertices.get(i).getLatitude();
        x1 = vertices.get(0).getLongitude();
        y1 = vertices.get(0).getLatitude();
        a = x0 * y1 - x1 * y0;
        signedArea += a;
        centroid.setLongitude(centroid.getLongitude() + (x0 + x1) * a);
        centroid.setLatitude(centroid.getLatitude() + (y0 + y1) * a);

        signedArea *= 0.5;
        centroid.setLongitude(centroid.getLongitude() / (6.0 * signedArea));
        centroid.setLatitude(centroid.getLatitude() / (6.0 * signedArea));

        return centroid;
    }

}
