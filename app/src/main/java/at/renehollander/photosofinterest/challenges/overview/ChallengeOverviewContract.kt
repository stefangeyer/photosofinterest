package at.renehollander.photosofinterest.challenges.overview

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Challenge
import org.threeten.bp.LocalDateTime

interface ChallengeOverviewContract {
    interface View : BaseView {
        fun stopRefreshing()
        fun setOnDataReloadListener(listener: OnDataReloadListener)
        fun setOnShowDetailsListener(listener: OnShowDetailsListener)
        fun getAdapter(): Adapter

        interface OnDataReloadListener {
            fun onReload()
        }

        interface OnShowDetailsListener {
            fun showDetails(challenge: Challenge)
        }
    }

    interface ViewHolder : BaseView {
        fun updateImage(uri: String)
        fun updateTitle(title: String)
        fun updateEnd(end: LocalDateTime)
        fun updateLocations(locations: List<String>)
        fun showImage(title: String, uri: String)
        fun showDetails()
        fun showUploads()
    }

    interface ViewHolderPresenter : BasePresenter<ViewHolder> {
        fun bind()
        fun changePosition(position: Int)
        fun onImageClicked()
        fun onDetailsButtonClicked()
        fun onUploadsButtonClicked()
    }

    interface Adapter {
        fun setAll(posts: List<Challenge>)
        fun addItem(post: Challenge)
        fun removeItem(post: Challenge)
        fun getItemAt(position: Int): Challenge
        fun getItems(): List<Challenge>
        fun notifyAdapter()
        fun showChallenge(challenge: Challenge)
    }
}