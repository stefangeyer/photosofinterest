package at.renehollander.photosofinterest.scoreboard.ownentry

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.ScoreboardEntry

/**
 * Contract for the challenges parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ScoreboardOwnEntryContract {
    interface View : BaseView {
        fun updateRank(rank: Int)
        fun updateUserImage(uri: String)
        fun updateName(name: String)
        fun updateScore(score: Int)
        fun updateIsLeader(isLeader: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun setEntry(entry: ScoreboardEntry?)
        fun setRank(rank: Int)
        fun update()
    }
}
