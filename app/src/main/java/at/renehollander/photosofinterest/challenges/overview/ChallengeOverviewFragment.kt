package at.renehollander.photosofinterest.challenges.overview

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import javax.inject.Inject

class ChallengeOverviewFragment @Inject constructor() : DaggerFragment(), ChallengeOverviewContract.View {

    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
    val adapter: ChallengeOverviewAdapter = ChallengeOverviewAdapter()

    var reloadListener = object : ChallengeOverviewContract.View.OnDataReloadListener {
        override fun onReload() {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = this.layoutManager

        swipeRefreshLayout.setOnRefreshListener {
            this.reloadListener.onReload()
        }

        this.reloadListener.onReload()
    }

    override fun onPause() {
        super.onPause()

        recyclerView.adapter = null
        recyclerView.layoutManager = null
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun setOnDataReloadListener(listener: ChallengeOverviewContract.View.OnDataReloadListener) {
        this.reloadListener = listener
    }

    override fun getAdapter(): ChallengeOverviewContract.Adapter = this.adapter
}