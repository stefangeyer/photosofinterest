package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.data.Scoreboard
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import at.renehollander.photosofinterest.data.source.ScoreboardDataSource
import javax.inject.Inject

class ScoreboardPresenter @Inject constructor(
        private var scoreboardDataSource: ScoreboardDataSource
) : ScoreboardContract.Presenter {
    override fun fetchScores() {
        scoreboardDataSource.loadGlobalScoreboard(object : GetRecordCallback<Scoreboard> {
            override fun onRecordLoaded(record: Scoreboard) {
                view?.updateScores(record.scores)
            }

            override fun onDataNotAvailable() {
            }
        })
    }

    private var view: ScoreboardContract.View? = null

    override fun takeView(view: ScoreboardContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}
