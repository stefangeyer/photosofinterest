package at.renehollander.photosofinterest.challenge

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

/**
 * Contract for the feed parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengeContract {
    interface View : BaseView {
        fun updatePosts(posts: List<Post>)

        // Errors
        fun showCannotReload()
    }

    interface Presenter : BasePresenter<View> {
        fun fetchPosts()
    }
}
