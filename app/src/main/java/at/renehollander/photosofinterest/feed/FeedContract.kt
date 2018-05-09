package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

/**
 * Contract for the feed parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface FeedContract {
    interface View : BaseView {
        fun updateRisingPosts(posts: List<Post>)
        fun updateRecentPosts(posts: List<Post>)
        fun updateTopPosts(posts: List<Post>)

        // Errors
        fun showCannotReload()
    }

    interface Presenter : BasePresenter<View> {
        fun fetchRecentPosts()
        fun fetchRisingPosts()
        fun fetchTopPosts()
    }
}