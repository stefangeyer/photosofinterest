package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

interface PostContract {
    interface View : BaseView {
        fun init(post: Post)
        fun updateTitle(title: String)
        fun updateChallengeName(name: String)
        fun updateLocationName(name: String)
        fun updateUserImage(uri: String)
        fun updateImage(uri: String)
        fun updateUpvotes(upvotes: Int)
        fun updateDownvotes(downvotes: Int)
        fun showChallengeDetails()
        fun enableVoteButtons(enabled: Boolean)
        fun showImageDetails(title: String, uri: String)
    }

    interface Presenter : BasePresenter<View> {
        fun init(post: Post)
        fun onImageClicked()
        fun onInformationClicked()
        fun onUpvoteButtonClicked()
        fun onDownvoteButtonClicked()
    }
}