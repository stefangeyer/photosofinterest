package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengeDataSource {
    fun loadChallenges(callback: LoadRecordCallback<Challenge>)
    fun loadChallengeDetails(challenge: Challenge, callback: GetRecordCallback<Challenge>)
}