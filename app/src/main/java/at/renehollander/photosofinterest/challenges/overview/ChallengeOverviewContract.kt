package at.renehollander.photosofinterest.challenges.overview

import at.renehollander.photosofinterest.BaseAdapter
import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Challenge
import org.threeten.bp.Duration

interface ChallengeOverviewContract {
    interface View : BaseView {
        fun stopRefreshing()
        fun setOnDataReloadListener(listener: OnDataReloadListener)
        fun getAdapter(): Adapter

        interface OnDataReloadListener {
            fun onReload()
        }
    }

    interface ViewHolder : BaseView {
        fun updateImage(uri: String)
        fun updateTitle(title: String)
        fun updateEnd(between: Duration)
        fun updateLocations(locations: List<String>)
        fun showImage(title: String, uri: String)
    }

    interface ViewHolderPresenter : BasePresenter<ViewHolder> {
        fun bind()
        fun changePosition(position: Int)
        fun onImageClicked()
        fun onDetailsButtonClicked()
        fun onUploadsButtonClicked()
    }

    interface Adapter: BaseAdapter<Challenge> {
        fun showChallenge(challenge: Challenge, showUploads: Boolean)
        fun setOnShowDetailsListener(listener: OnShowDetailsListener)

        interface OnShowDetailsListener {
            fun showDetails(challenge: Challenge, showUploads: Boolean)
        }
    }
}