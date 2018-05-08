package at.renehollander.photosofinterest.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.feed.post.PostAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragment @Inject constructor() : DaggerFragment(), FeedContract.View {

    @Inject
    lateinit var presenter: FeedContract.Presenter

    val posts = mutableListOf<Post>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = PostAdapter(this.posts)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}
