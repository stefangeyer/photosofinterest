package at.renehollander.photosofinterest.profile

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.User

/**
 * Contract for the profile parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ProfileContract {

    interface View : BaseView {
        fun updateProfileImage(uri: String)
        fun updateName(name: String)
        fun updateScore(score: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun setUser(user: User)
        fun updateUser()
    }
}