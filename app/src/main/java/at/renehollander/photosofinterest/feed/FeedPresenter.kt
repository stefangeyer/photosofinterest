package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import javax.inject.Inject

class FeedPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val loadPosts: LoadPosts
) : FeedContract.Presenter {

    private var view: FeedContract.View? = null

    override fun takeView(view: FeedContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun fetchPosts() {
        this.useCaseHandler.execute(this.loadPosts, LoadPosts.RequestValues(), {
            // Success
            response ->
            this.view?.updatePosts(response.posts)
        }, {
            // Failure
            this.view?.showCannotReload()
        })
    }
}