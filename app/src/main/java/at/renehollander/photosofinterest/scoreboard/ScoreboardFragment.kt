package at.renehollander.photosofinterest.scoreboard

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.ScoreboardEntry
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.scoreboard.entry.ScoreboardEntryAdapter
import at.renehollander.photosofinterest.scoreboard.ownentry.ScoreboardOwnEntryFragment
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@ActivityScoped
class ScoreboardFragment @Inject constructor() : DaggerFragment(), ScoreboardContract.View {

    @Inject
    lateinit var presenter: ScoreboardContract.Presenter

    @Inject
    lateinit var ownEntryFragment: ScoreboardOwnEntryFragment

    private val adapter: ScoreboardEntryAdapter = ScoreboardEntryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        scoreboard_list.addItemDecoration(dividerItemDecoration)
        scoreboard_list.layoutManager = layoutManager
        scoreboard_list.adapter = this.adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)

        this.presenter.fetchScores()

        if (this.presenter.getUser() != null) {
            onSignIn(this.presenter.getUser()!!)
        }
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)

        presenter.dropView()
    }

    override fun onSignIn(user: User) {
        ownEntryFragment.presenter.setEntry(ScoreboardEntry(null, user, 1000))
        ownEntryFragment.presenter.setRank(42)
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.ownEntry_container, ownEntryFragment)
        ft.commit()
    }

    override fun onSignOut() {
        val ft = childFragmentManager.beginTransaction()
        ft.remove(this.ownEntryFragment)
        ft.commit()
    }

    override fun updateScores(scores: List<ScoreboardEntry>) {
        this.adapter.setAll(scores)
    }

    override fun showCannotReload() {
        Toast.makeText(activity, "Cannot fetch scores", Toast.LENGTH_SHORT).show()
    }

}
