package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the scoreboard view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ScoreboardContract {

    interface View : BaseView
    interface Presenter : BasePresenter<View>
}