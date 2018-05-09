package at.renehollander.photosofinterest.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.challenges.overview.ChallengeOverviewFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

class ChallengesFragment @Inject constructor() : DaggerFragment(), ChallengesContract.View {

    @Inject
    lateinit var presenter: ChallengesContract.Presenter

    @Inject
    lateinit var overviewFragmentProvider: Provider<ChallengeOverviewFragment>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
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
