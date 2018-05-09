package at.renehollander.photosofinterest.auth

import at.renehollander.photosofinterest.PhotosOfInterest
import javax.inject.Inject

class AuthPresenter @Inject constructor(private var application: PhotosOfInterest) : AuthContract.Presenter {

    private var view: AuthContract.View? = null

    override fun takeView(view: AuthContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun facebookSignIn() {
        application.login()
        view?.close() // TODO: not implemented
    }

    override fun googleSignIn() {
        application.login()
        view?.close() // TODO: not implemented
    }

}
