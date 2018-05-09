package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
) : ChallengeContract.Presenter {

    private var view: ChallengeContract.View? = null

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
}