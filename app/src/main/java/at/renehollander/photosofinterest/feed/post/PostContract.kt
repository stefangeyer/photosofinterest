package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

interface PostContract {
    interface ViewHolder : BaseView {
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

    interface ViewHolderPresenter : BasePresenter<ViewHolder> {
        fun onImageClicked()
        fun onInformationClicked()
        fun onUpvoteButtonClicked()
        fun onDownvoteButtonClicked()
    }

    interface AdapterView: BaseView {
        fun update(posts: List<Post>)
        fun notifyAdapter()
    }

    interface AdapterPresenter: BasePresenter<AdapterView> {
        fun getItemCount(): Int
        fun getItemAt(position: Int): Post
        fun onDataChange(posts: List<Post>)
    }
}