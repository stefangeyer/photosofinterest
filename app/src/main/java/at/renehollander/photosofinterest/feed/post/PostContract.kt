package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.BaseAdapter
import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Post

interface PostContract {
    interface View : BaseView {
        fun stopRefreshing()
        fun setOnDataReloadListener(listener: OnDataReloadListener)
        fun getAdapter(): Adapter

        interface OnDataReloadListener {
            fun onReload()
        }
    }

    interface ViewHolder : BaseView {
        fun bind()
        fun updateTitle(title: String)
        fun updateChallengeName(name: String)
        fun updateLocationName(name: String)
        fun updateUserImage(uri: String)
        fun updateImage(uri: String)
        fun updateVotes(votes: Int)
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

    interface Adapter : BaseAdapter<Post>
}