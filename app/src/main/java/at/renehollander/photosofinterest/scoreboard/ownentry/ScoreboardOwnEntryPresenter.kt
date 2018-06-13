package at.renehollander.photosofinterest.scoreboard.ownentry

import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.data.ScoreboardEntry
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ScoreboardOwnEntryPresenter @Inject constructor(
) : ScoreboardOwnEntryContract.Presenter {

    private var view: ScoreboardOwnEntryContract.View? = null
    private var rank: Int? = null
    private var entry: ScoreboardEntry? = null

    override fun takeView(view: ScoreboardOwnEntryContract.View) {
        this.view = view
        EventBus.getDefault().register(this)
    }

    override fun dropView() {
        this.view = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: SignInEvent) {
        setRank(100)
        setEntry(ScoreboardEntry(null, event.user, 400)) // TODO get actual score
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
            view?.updateUserImage(entry!!.user.image)
            view?.updateName(entry!!.user.name)
            view?.updateScore(entry!!.score)
            view?.updateIsLeader(rank == 1)
        }
    }

}