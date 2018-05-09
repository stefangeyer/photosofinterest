package at.renehollander.photosofinterest.profile

import javax.inject.Inject

class ProfilePresenter @Inject constructor() : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null

    override fun takeView(view: ProfileContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}