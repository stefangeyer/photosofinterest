package at.renehollander.photosofinterest.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider


class FeedFragment @Inject constructor() : DaggerFragment(), FeedContract.View {

    @Inject
    lateinit var presenter: FeedContract.Presenter

    @Inject
    lateinit var postFragmentProvider: Provider<PostFragment>

    private var adapter: FeedFragmentPagerAdapter? = null
    private var rising: PostFragment? = null
    private var recent: PostFragment? = null
    private var top: PostFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.rising = this.postFragmentProvider.get()
        this.recent = this.postFragmentProvider.get()
        this.top = this.postFragmentProvider.get()

        this.adapter = FeedFragmentPagerAdapter(childFragmentManager, context!!,
                this.rising!!, this.recent!!, this.top!!)

        this.rising?.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                this@FeedFragment.presenter.fetchRisingPosts()
            }
        })

        this.recent?.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                this@FeedFragment.presenter.fetchRecentPosts()
            }
        })

        this.top?.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                this@FeedFragment.presenter.fetchTopPosts()
            }
        })


        viewPager.adapter = this.adapter
        tabLayout.setupWithViewPager(viewPager)

        // TODO change
        this.presenter.fetchRisingPosts()
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        // TODO change
        this.presenter.fetchRisingPosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateRisingPosts(posts: List<Post>) {
        this.rising?.adapter?.setAll(posts)
        this.rising?.stopRefreshing()
    }

    override fun updateRecentPosts(posts: List<Post>) {
        this.recent?.adapter?.setAll(posts)
        this.recent?.stopRefreshing()
    }

    override fun updateTopPosts(posts: List<Post>) {
        this.top?.adapter?.setAll(posts)
        this.top?.stopRefreshing()
    }

    override fun showCannotReload() {
        Toast.makeText(activity, getString(R.string.feed_cannot_reload), Toast.LENGTH_SHORT).show()
    }


}
