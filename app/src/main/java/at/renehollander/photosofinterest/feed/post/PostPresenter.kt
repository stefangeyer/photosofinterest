package at.renehollander.photosofinterest.feed.post

import javax.inject.Inject

class PostPresenter @Inject constructor() : PostContract.Presenter {

    private var view: PostContract.View? = null

    override fun takeView(view: PostContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}