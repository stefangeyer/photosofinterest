package at.renehollander.photosofinterest.profile

import at.renehollander.photosofinterest.data.User
import javax.inject.Inject

class ProfilePresenter @Inject constructor() : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null

    private var user: User? = null

    override fun takeView(view: ProfileContract.View) {
        this.view = view
        updateUser()
    }

    override fun setUser(user: User) {
        this.user = user
        updateUser()
    }

    override fun updateUser() {
        if (user != null) {
            view?.updateProfileImage(user!!.image.uri)
            view?.updateName(user!!.name)
            view?.updateScore(1234)
        }
    }

    override fun dropView() {
        this.view = null
    }
}