package at.renehollander.photosofinterest.feed

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.feed.post.PostAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject


class FeedFragment @Inject constructor() : DaggerFragment(), FeedContract.View {

    @Inject
    lateinit var presenter: FeedContract.Presenter

    private lateinit var adapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener({
            fetchPosts()
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                fetchPosts()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                fetchPosts()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
        })

        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)

        adapter = PostAdapter(activity!!.application as PhotosOfInterest)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = this.adapter
        recyclerView.addItemDecoration(dividerItemDecoration)

        fetchPosts()
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updatePosts(posts: List<Post>) {
        this.adapter.setAll(posts)
        stopRefreshing()
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showCannotReload() {
        Toast.makeText(activity, getString(R.string.feed_cannot_reload), Toast.LENGTH_SHORT).show()
    }

    private fun fetchPosts() {
        when (tabLayout.selectedTabPosition) {
            0 -> {
                this.presenter.fetchRisingPosts()
            }
            1 -> {
                this.presenter.fetchRecentPosts()
            }
            2 -> {
                this.presenter.fetchTopPosts()
            }
        }
    }
}
