package at.renehollander.photosofinterest.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import dagger.Lazy
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge.*
import javax.inject.Inject

class ChallengeFragment @Inject constructor() : DaggerFragment(), ChallengeContract.View {

    @Inject
    lateinit var presenter: ChallengeContract.Presenter

    @Inject
    lateinit var adapter: Lazy<ChallengeFragmentPagerAdapter>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewPager.adapter = adapter.get()
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
    }

    override fun showCannotReload() {
    }
}
