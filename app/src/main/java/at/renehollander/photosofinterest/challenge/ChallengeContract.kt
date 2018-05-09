package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsContract
import at.renehollander.photosofinterest.data.Challenge

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
        fun startLogin()
    }

    interface Presenter : BasePresenter<View> {
        fun takePhoto()
        fun photoTaken(photo: Bitmap)

        fun setChallenge(challenge: Challenge?)
        fun update()
    }
}
