package at.renehollander.photosofinterest.feed.post

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import javax.inject.Inject

class PostFragment @Inject constructor() : DaggerFragment(), PostContract.View {

    @Inject
    lateinit var application: PhotosOfInterest

    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
    lateinit var adapter: PostAdapter

    var reloadListener = object : PostContract.View.OnDataReloadListener {
        override fun onReload() {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.adapter = PostAdapter(application)

        swipeRefreshLayout.setOnRefreshListener {
            this.reloadListener.onReload()
        }

        this.reloadListener.onReload()
    }

    override fun onResume() {
        super.onResume()

        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = this.layoutManager
    }

    override fun onPause() {
        super.onPause()

        recyclerView.adapter = null
        recyclerView.layoutManager = null
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun setOnDataReloadListener(listener: PostContract.View.OnDataReloadListener) {
        this.reloadListener = listener
    }

    override fun getAdapter(): PostContract.Adapter = this.adapter
}