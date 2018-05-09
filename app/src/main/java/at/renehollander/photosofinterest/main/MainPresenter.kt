package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.UseCaseHandler
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val application: PhotosOfInterest,
        private val useCaseHandler: UseCaseHandler
) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun takeView(view: MainContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun signIn() {
        this.view?.startSignIn()
    }

    override fun signOut() {
        application.logout()
        this.view?.startSignOut()
    }
}
