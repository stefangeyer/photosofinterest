package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsContract
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Post

/**
 * Contract for the feed parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengeContract {
    interface View : BaseView {
        fun startPhotoTake()
        fun getDetailsPresenter(): ChallengeDetailsContract.Presenter
        fun updateChallengePosts(posts: List<Post>)
        fun showCannotReload()
        fun startLogin()
        fun showDetails()
        fun showUploads()
    }

    interface Presenter : BasePresenter<View> {
        fun takePhoto()
        fun photoTaken(photo: Bitmap)

        fun setChallenge(challenge: Challenge?)
        fun loadChallengePosts()
        fun update()
    }
}
