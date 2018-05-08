package at.renehollander.photosofinterest.scoreboard

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class ScoreboardFragment @Inject constructor() : DaggerFragment(), ScoreboardContract.View {

    @Inject
    lateinit var presenter: ScoreboardContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    private lateinit var dataset: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataset()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false)
        val list = rootView.findViewById<RecyclerView>(R.id.scoreboard_list);
        val layoutManager = LinearLayoutManager(activity)
        list.layoutManager = layoutManager
        list.adapter = ScoreboardAdapter(dataset)

        val dividerItemDecoration = DividerItemDecoration(list.getContext(), layoutManager.getOrientation())
        list.addItemDecoration(dividerItemDecoration)

        return rootView
    }

    private fun initDataset() {
        dataset = Array(3000, { i -> "User $i" })
    }
}
