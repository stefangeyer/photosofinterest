package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.firestore.FirebaseFirestore
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
        val db: FirebaseFirestore
) : ScoreboardDataSource {

    override fun loadGlobalScoreboard(callback: GetRecordCallback<Scoreboard>) {
        callback.onRecordLoaded(Scoreboard("Global",
                (1..50).map { ScoreboardEntry(null, User(email = "user$it@example.com", name = "User $it", image = Image("http://i.pravatar.cc/256?img=$it")), it * 10) }.reversed(),
                null))
    }

    override fun loadChallengeScoreboard(challenge: Challenge, callback: GetRecordCallback<Scoreboard>) {
        callback.onRecordLoaded(Scoreboard("Global", mutableListOf(
                ScoreboardEntry(null, User(email = "user1@example.com", name = "User 1", image = Image("img1")), 20),
                ScoreboardEntry(null, User(email = "user2@example.com", name = "User 2", image = Image("img2")), 30),
                ScoreboardEntry(null, User(email = "user3@example.com", name = "User 3", image = Image("img2")), 100)
        ), challenge))
    }

}