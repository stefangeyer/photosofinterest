package at.renehollander.photosofinterest.scoreboard.ownentry

import at.renehollander.photosofinterest.data.ScoreboardEntry
import javax.inject.Inject

class ScoreboardOwnEntryPresenter @Inject constructor(
) : ScoreboardOwnEntryContract.Presenter {

    private var view: ScoreboardOwnEntryContract.View? = null
    private var rank: Int? = null
    private var entry: ScoreboardEntry? = null

    override fun takeView(view: ScoreboardOwnEntryContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun setRank(rank: Int) {
        this.rank = rank
        update()
    }

    override fun setEntry(entry: ScoreboardEntry?) {
        this.entry = entry
        update()
    }

    override fun update() {
        if (rank != null && entry != null) {
            view?.updateRank(rank!!)
            view?.updateUserImage(entry!!.user.image.uri)
            view?.updateName(entry!!.user.name)
            view?.updateScore(entry!!.score)
            view?.updateIsLeader(rank == 1)
        }
    }

}