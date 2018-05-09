package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.source.ChallengeDataSource.Filter.*
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
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
) : ChallengeDataSource {
    override fun loadChallenges(filter: ChallengeDataSource.Filter, callback: LoadRecordCallback<Challenge>) {
        val challenge1 = Challenge(
                "Challenge 1",
                Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg"),
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1),
                "Description 1", mutableListOf(), mutableListOf())
        val challenge2 = Challenge(
                "Challenge 2",
                Image("https://webheimat.at/aktiv/Urlaub/Tipps/Woerthersee-Sommer-Events/Woerthersee-Sommer_high.jpg"),
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(4).plusHours(2),
                "Description 2", mutableListOf(), mutableListOf())

        val challenge3 = Challenge(
                "Challenge 3",
                Image("https://www.presseteamaustria.at/wp-content/uploads/2016/07/04-Restaurant-Maria-Loretto-Klagenfurt-45.jpg"),
                LocalDateTime.now().minusDays(4), LocalDateTime.now().plusDays(7).plusHours(2),
                "Description 3", mutableListOf(), mutableListOf())


        val out = when (filter) {
            NEARBY -> listOf(challenge3, challenge2)
            ONGOING -> listOf(challenge1, challenge3)
            ALL -> listOf(challenge1, challenge2, challenge3)
        }

        callback.onRecordsLoaded(out)
    }

    override fun loadChallengeDetails(challenge: Challenge, callback: GetRecordCallback<Challenge>) {
        callback.onRecordLoaded(Challenge(
                "Challenge 2",
                Image("https://webheimat.at/aktiv/Urlaub/Tipps/Woerthersee-Sommer-Events/Woerthersee-Sommer_high.jpg"),
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(4).plusHours(2),
                "Description 2", mutableListOf(), mutableListOf()))
    }

}