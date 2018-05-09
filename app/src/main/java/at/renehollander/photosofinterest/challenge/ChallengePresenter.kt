package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.data.Challenge
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
) : ChallengeContract.Presenter {

    private var view: ChallengeContract.View? = null

    private var challenge: Challenge? = null

    override fun takeView(view: ChallengeContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun takePhoto() {
        this.view?.startPhotoTake()
    }

    override fun photoTaken(photo: Bitmap) {
    }

    override fun setChallenge(challenge: Challenge?) {
        this.challenge = challenge
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
    }

    override fun update() {
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
        this.view?.getDetailsPresenter()?.update()
    }
}