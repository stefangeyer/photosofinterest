package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.ScoreboardEntry
import at.renehollander.photosofinterest.data.User

/**
 * Contract for the scoreboard view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ScoreboardContract {

    interface View : BaseView {
        fun updateScores(scores: List<ScoreboardEntry>)
        fun showCannotReload()
        fun onSignIn(user: User)
        fun onSignOut()
    }

    interface Presenter : BasePresenter<View> {
        fun fetchScores()
        fun getUser(): User?
    }
}