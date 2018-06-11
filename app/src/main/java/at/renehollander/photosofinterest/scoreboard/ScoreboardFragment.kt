package at.renehollander.photosofinterest.scoreboard

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.ScoreboardEntry
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.scoreboard.entry.ScoreboardEntryAdapter
import at.renehollander.photosofinterest.scoreboard.ownentry.ScoreboardOwnEntryFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_scoreboard.*
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

        ownEntryFragment.presenter.setRank(100)
        ownEntryFragment.presenter.setEntry(ScoreboardEntry(
                null, User(
                "test@example.com", "Arnold Schwarzenegger", Image("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Arnold_Schwarzenegger_February_2015.jpg/433px-Arnold_Schwarzenegger_February_2015.jpg")
        ), 400))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun onSignIn(user: User) {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.ownEntry_container, ownEntryFragment)
        ft.commit()
    }

    override fun onSignOut() {
    }

    override fun updateScores(scores: List<ScoreboardEntry>) {
        this.adapter.setAll(scores)
    }

    override fun showCannotReload() {
        Toast.makeText(activity, "Cannot fetch scores", Toast.LENGTH_SHORT).show()
    }

}
