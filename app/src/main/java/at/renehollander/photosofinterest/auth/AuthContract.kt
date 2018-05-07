package at.renehollander.photosofinterest.auth

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the auth view
 *
 * @author Rene Hollander
 * @version 1.0
 */
interface AuthContract {

    interface View : BaseView {
    }

    interface Presenter : BasePresenter<View> {
    }
}
