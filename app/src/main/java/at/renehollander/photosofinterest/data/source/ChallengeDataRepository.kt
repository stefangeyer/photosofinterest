package at.renehollander.photosofinterest.data.source

import android.util.Log
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.PointOfInterest
import at.renehollander.photosofinterest.data.Region
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import at.renehollander.photosofinterest.timestampToLocalDateTime
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import javax.inject.Inject

/**
 * Implementation of DataSource using a Repository Pattern.
 * This can be used to have both, a local and a remote Repository
 * and merge their operations in this class
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
@ApplicationScoped
class ChallengeDataRepository @Inject constructor(
        private val db: FirebaseFirestore
) : ChallengeDataSource {
    override fun loadChallenges(filter: ChallengeDataSource.Filter, callback: LoadRecordCallback<Challenge>) {
        db.collection("challenges").get().addOnSuccessListener {
            @Suppress("UNCHECKED_CAST")
            val challenges = it.documents.map {
                val challenge = Challenge()
                challenge.id = it.id
                challenge.title = it["title"] as String
                challenge.image = it["image"] as String
                challenge.start = timestampToLocalDateTime(it["start"] as Timestamp)
                challenge.end = timestampToLocalDateTime(it["end"] as Timestamp)
                challenge.description = it["description"] as String
                challenge.regions = (it["regions"] as List<Map<String, Any?>>).map mapi@{
                    val region = Region()
                    region.description = it["description"] as String
                    region.region = it["region"] as List<GeoPoint>
                    return@mapi region
                }
                return@map challenge
            }.toList()
            callback.onRecordsLoaded(challenges)
            Log.d("ChallengeDataRepository", challenges.toString())
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }

//        val challenge1 = Challenge(
//                title = "Flüsse oder so",
//                image = Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg"),
//                start = LocalDateTime.now().minusDays(1), end = LocalDateTime.now().plusDays(1),
//                description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
//                regions = mutableListOf(Region("Carinthia", mutableListOf()), Region("Lower Austria", mutableListOf())), pois = mutableListOf())
//        val challenge2 = Challenge(
//                title = "Kärntner Seen",
//                image = Image("https://webheimat.at/aktiv/Urlaub/Tipps/Woerthersee-Sommer-Events/Woerthersee-Sommer_high.jpg"),
//                start = LocalDateTime.now().minusDays(1), end = LocalDateTime.now().plusDays(4).plusHours(2),
//                description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
//                regions = mutableListOf(Region("Carinthia", mutableListOf())), pois = mutableListOf())
//
//        val challenge3 = Challenge(
//                title = "Wiener Denkmäler",
//                image = Image("https://www.wien.info/media/images/altstadt-panorama-mit-stephansdom-und-karlskirche-19to1.jpeg"),
//                start = LocalDateTime.now().minusDays(4), end = LocalDateTime.now().plusDays(7).plusHours(2),
//                description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
//                regions = mutableListOf(Region("Wien oida", mutableListOf())), pois = mutableListOf())
//
//
//        val out = when (filter) {
//            NEARBY -> listOf(challenge3, challenge2)
//            ONGOING -> listOf(challenge1, challenge3)
//            ALL -> listOf(challenge1, challenge2, challenge3)
//        }
//
//        callback.onRecordsLoaded(out)
    }

    override fun loadChallengeDetails(challenge: Challenge, callback: GetRecordCallback<Challenge>) {
        db.collection("challenges").document(challenge.id).collection("pois").get().addOnSuccessListener {
            @Suppress("UNCHECKED_CAST")
            challenge.pois = it.documents.map {
                val poi = PointOfInterest()
                poi.id = it.id
                poi.name = it["name"] as String
                poi.location = it["location"] as GeoPoint
                poi.radius = it["radius"] as Double
                return@map poi
            }.toList()
            callback.onRecordLoaded(challenge)
            Log.d("ChallengeDataRepository", challenge.pois.toString())
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

}