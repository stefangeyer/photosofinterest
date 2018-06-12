package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.data.Scoreboard
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import at.renehollander.photosofinterest.data.source.ScoreboardDataSource
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ScoreboardPresenter @Inject constructor(
        private var scoreboardDataSource: ScoreboardDataSource
) : ScoreboardContract.Presenter {

    private var view: ScoreboardContract.View? = null
    private var user: User? = null

    override fun takeView(view: ScoreboardContract.View) {
        this.view = view
        EventBus.getDefault().register(this)
    }

    override fun dropView() {
        this.view = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: SignInEvent) {
        this.user = event.user
        view?.onSignIn(event.user)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: SignOutEvent) {
        this.user = null
        view?.onSignOut()
    }

    override fun fetchScores() {
        scoreboardDataSource.loadGlobalScoreboard(object : GetRecordCallback<Scoreboard> {
            override fun onRecordLoaded(record: Scoreboard) {
                view?.updateScores(record.scores)
            }

            override fun onDataNotAvailable() {
            }
        })
    }

    override fun getUser(): User? = this.user
}
