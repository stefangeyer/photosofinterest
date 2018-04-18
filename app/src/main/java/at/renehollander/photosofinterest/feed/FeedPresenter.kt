package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.UseCaseHandler
import javax.inject.Inject

class FeedPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler
): FeedContract.Presenter {

    private var view: FeedContract.View? = null

    override fun takeView(view: FeedContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}