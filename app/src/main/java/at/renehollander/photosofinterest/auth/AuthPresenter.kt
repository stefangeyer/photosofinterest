package at.renehollander.photosofinterest.auth

import javax.inject.Inject

class AuthPresenter @Inject constructor() : AuthContract.Presenter {

    private var view: AuthContract.View? = null

    override fun takeView(view: AuthContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun facebookSignIn() {
        view?.close()
        TODO("not implemented")
    }

    override fun googleSignIn() {
        view?.close()
        TODO("not implemented")
    }

}
