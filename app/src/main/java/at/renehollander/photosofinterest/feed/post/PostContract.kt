package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

interface PostContract {
    interface ViewHolder : BaseView {
        fun bind()
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
        fun updateViewHolderPosition(position: Int)
    }

    interface ViewHolderPresenter : BasePresenter<ViewHolder> {
        fun onImageClicked()
        fun onInformationClicked()
        fun onUpvoteButtonClicked()
        fun onDownvoteButtonClicked()
        fun onPositionChanged(position: Int)
        fun onBind()
    }

    interface Adapter : BaseView {
        fun setAll(posts: List<Post>)
        fun addItem(post: Post)
        fun removeItem(post: Post)
        fun getItemAt(position: Int): Post
        fun getItems(): List<Post>
        fun notifyAdapter()
    }
}