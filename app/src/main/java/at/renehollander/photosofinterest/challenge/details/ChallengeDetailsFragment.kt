package at.renehollander.photosofinterest.challenge.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ChallengeDetailsFragment @Inject constructor() : DaggerFragment(), ChallengeDetailsContract.View {

    @Inject
    lateinit var presenter: ChallengeDetailsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
