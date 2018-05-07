package at.renehollander.photosofinterest.feed.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import dagger.android.DaggerFragment
import javax.inject.Inject

class PostFragment @Inject constructor() : DaggerFragment(), PostContract.View {

    @Inject
    lateinit var presenter: PostContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun init() {

    }
}
