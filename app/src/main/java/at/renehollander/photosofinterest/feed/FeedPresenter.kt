package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.feed.domain.model.RequestFilter
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

    override fun fetchRecentPosts() {
        fetchPosts(RequestFilter.RECENT)
    }

    override fun fetchRisingPosts() {
        fetchPosts(RequestFilter.RISING)
    }

    override fun fetchTopPosts() {
        fetchPosts(RequestFilter.TOP)
    }

    private fun fetchPosts(filter: RequestFilter) {
        this.useCaseHandler.execute(this.loadPosts, LoadPosts.RequestValues(filter), {
            // Success
            response ->
            when (response.filter) {
                RequestFilter.RISING -> this.view?.updateRisingPosts(response.posts)
                RequestFilter.RECENT -> this.view?.updateRecentPosts(response.posts)
                RequestFilter.TOP -> this.view?.updateTopPosts(response.posts)
            }
        }, {
            // Failure
            this.view?.showCannotReload()
        })
    }
}