package at.renehollander.photosofinterest.challenge

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

    override fun fetchPosts() {
    }
}