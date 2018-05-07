package at.renehollander.photosofinterest.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.feed.post.PostFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

class FeedFragment @Inject constructor() : DaggerFragment(), FeedContract.View {

    @Inject
    lateinit var presenter: FeedContract.Presenter

    @Inject
    lateinit var postProvider: Provider<PostFragment>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
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
