package at.renehollander.photosofinterest.profile

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the profile view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ProfileContract {

    interface View : BaseView
    interface Presenter : BasePresenter<View>
}