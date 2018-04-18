package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.UseCaseHandler
import javax.inject.Inject

class ScoreboardPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler
): ScoreboardContract.Presenter {

    private var view: ScoreboardContract.View? = null

    override fun takeView(view: ScoreboardContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}