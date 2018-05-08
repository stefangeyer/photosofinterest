package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.UseCaseHandler
import javax.inject.Inject

class ChallengesPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler
) : ChallengesContract.Presenter {

    private var view: ChallengesContract.View? = null

    override fun takeView(view: ChallengesContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}