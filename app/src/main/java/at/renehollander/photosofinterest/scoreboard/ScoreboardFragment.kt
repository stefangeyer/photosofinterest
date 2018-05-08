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
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.scoreboard.entry.ScoreboardEntryAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_scoreboard.*
import javax.inject.Inject

@ActivityScoped
class ScoreboardFragment @Inject constructor() : DaggerFragment(), ScoreboardContract.View {

    @Inject
    lateinit var presenter: ScoreboardContract.Presenter

    private val adapter: ScoreboardEntryAdapter = ScoreboardEntryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.getOrientation())
        scoreboard_list.addItemDecoration(dividerItemDecoration)
        scoreboard_list.layoutManager = layoutManager
        scoreboard_list.adapter = this.adapter

    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)

        this.presenter.fetchScores()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateScores(scores: List<ScoreboardEntry>) {
        this.adapter.setAll(scores)
    }

    override fun showCannotReload() {
        Toast.makeText(activity, "Cannot fetch scores", Toast.LENGTH_SHORT).show()
    }

}
