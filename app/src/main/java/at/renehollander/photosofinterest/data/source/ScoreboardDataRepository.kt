package at.renehollander.photosofinterest.data.source

import android.util.Log
import at.renehollander.photosofinterest.data.Scoreboard
import at.renehollander.photosofinterest.data.ScoreboardEntry
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.functions.FirebaseFunctions
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
class ScoreboardDataRepository @Inject constructor(
        private val fbFunctions: FirebaseFunctions
) : ScoreboardDataSource {

    override fun loadGlobalScoreboard(callback: GetRecordCallback<Scoreboard>) {
        fbFunctions.getHttpsCallable("getGlobalScoreboard")
                .call()
                .addOnSuccessListener {
                    @Suppress("UNCHECKED_CAST")
                    callback.onRecordLoaded(Scoreboard(title = "Global", scores = (it.data as List<Map<String, Any>>).map { entryData ->
                        val userData = entryData["user"] as Map<String, Any>
                        return@map ScoreboardEntry(
                                user = User(
                                        id = userData["id"] as String,
                                        name = userData["name"] as String,
                                        image = userData["id"] as String
                                ),
                                score = entryData["score"] as Int,
                                post = null
                        )
                    }, challenge = null))
                }.addOnFailureListener {
                    Log.e(TAG, "Error getting scoreboard", it)
                    callback.onDataNotAvailable()
                }
    }

    companion object {
        const val TAG = "ScoreboardDataRepo"
    }
}
