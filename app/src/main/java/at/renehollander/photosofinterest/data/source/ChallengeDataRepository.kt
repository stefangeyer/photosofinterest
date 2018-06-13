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
import org.threeten.bp.LocalDateTime
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
            callback.onRecordsLoaded(when (filter) {
                ChallengeDataSource.Filter.ALL -> challenges
                ChallengeDataSource.Filter.NEARBY -> challenges.filter {
                    it.end.isAfter(LocalDateTime.now())
                }
                ChallengeDataSource.Filter.ONGOING -> challenges.filter {
                    it.end.isAfter(LocalDateTime.now())
                }
            })
            Log.d("ChallengeDataRepository", challenges.toString())
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
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