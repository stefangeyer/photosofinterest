package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.data.Scoreboard
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import at.renehollander.photosofinterest.data.source.ScoreboardDataSource
import at.renehollander.photosofinterest.data.source.UserManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ScoreboardPresenter @Inject constructor(
        private val scoreboardDataSource: ScoreboardDataSource,
        private val userManager: UserManager
) : ScoreboardContract.Presenter {
    override fun getUser(): User? {
        return userManager.getCurrentUser()
    }

    override fun updateView() {
        val scoreboard = this.scoreboard
        if (scoreboard != null) {
            view?.updateScores(scoreboard.scores)
        }
    }

    private var view: ScoreboardContract.View? = null
    private var scoreboard: Scoreboard? = null

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
        view?.onSignIn(event.user)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: SignOutEvent) {
        view?.onSignOut()
    }

    override fun fetchScores() {
        scoreboardDataSource.loadGlobalScoreboard(object : GetRecordCallback<Scoreboard> {
            override fun onRecordLoaded(record: Scoreboard) {
                scoreboard = record
                updateView()
            }

            override fun onDataNotAvailable() {
            }
        })
    }
}
