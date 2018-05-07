package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the challenges view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengesContract {
    interface View : BaseView
    interface Presenter : BasePresenter<View>
}