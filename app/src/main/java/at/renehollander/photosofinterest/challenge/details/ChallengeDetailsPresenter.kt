package at.renehollander.photosofinterest.challenge.details

import javax.inject.Inject

class ChallengeDetailsPresenter @Inject constructor(
) : ChallengeDetailsContract.Presenter {

    private var view: ChallengeDetailsContract.View? = null

    override fun takeView(view: ChallengeDetailsContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun fetchPosts() {
    }
}