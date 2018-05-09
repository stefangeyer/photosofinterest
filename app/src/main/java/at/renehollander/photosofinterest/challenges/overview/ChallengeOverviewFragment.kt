package at.renehollander.photosofinterest.challenges.overview

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import dagger.android.DaggerFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class ChallengeOverviewFragment @Inject constructor(): DaggerFragment() {

    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
    val adapter: ChallengeOverviewAdapter = ChallengeOverviewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = this.layoutManager
    }
}