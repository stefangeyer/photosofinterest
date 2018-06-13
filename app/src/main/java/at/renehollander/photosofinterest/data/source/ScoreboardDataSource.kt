package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Scoreboard

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ScoreboardDataSource {
    fun loadGlobalScoreboard(callback: GetRecordCallback<Scoreboard>)
}