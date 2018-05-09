package at.renehollander.photosofinterest.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.challenge.ChallengeFragment
import at.renehollander.photosofinterest.challenges.overview.ChallengeOverviewContract
import at.renehollander.photosofinterest.challenges.overview.ChallengeOverviewFragment
import at.renehollander.photosofinterest.data.Challenge
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge.*
import javax.inject.Inject
import javax.inject.Provider

class ChallengesFragment @Inject constructor() : DaggerFragment(), ChallengesContract.View, ChallengeOverviewContract.View.OnShowDetailsListener {

    @Inject
    lateinit var presenter: ChallengesContract.Presenter

    @Inject
    lateinit var overviewFragmentProvider: Provider<ChallengeOverviewFragment>

    @Inject
    lateinit var challengeFragmentProvider: Provider<ChallengeFragment>

    private var nearby: ChallengeOverviewFragment? = null
    private var ongoing: ChallengeOverviewFragment? = null
    private var all: ChallengeOverviewFragment? = null
    private var adapter: ChallengesFragmentPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.nearby = this.overviewFragmentProvider.get()
        this.ongoing = this.overviewFragmentProvider.get()
        this.all = this.overviewFragmentProvider.get()

        this.adapter = ChallengesFragmentPagerAdapter(childFragmentManager, context!!,
                this.nearby!!, this.ongoing!!, this.all!!)

        this.nearby?.setOnDataReloadListener(object : ChallengeOverviewContract.View.OnDataReloadListener {
            override fun onReload() {
                this@ChallengesFragment.presenter.fetchNearbyChallenges()
            }
        })
        this.nearby?.setOnShowDetailsListener(this)

        this.ongoing?.setOnDataReloadListener(object : ChallengeOverviewContract.View.OnDataReloadListener {
            override fun onReload() {
                this@ChallengesFragment.presenter.fetchOngoingChallenges()
            }
        })
        this.ongoing?.setOnShowDetailsListener(this)

        this.all?.setOnDataReloadListener(object : ChallengeOverviewContract.View.OnDataReloadListener {
            override fun onReload() {
                this@ChallengesFragment.presenter.fetchAllChallenges()
            }
        })
        this.all?.setOnShowDetailsListener(this)

        viewPager.adapter = this.adapter
        tabLayout.setupWithViewPager(viewPager)

        // TODO change
        this.presenter.fetchNearbyChallenges()
    }

    override fun showDetails(challenge: Challenge) {
        val fragment = challengeFragmentProvider.get()
        fragment.presenter.setChallenge(challenge)
        val res = activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.layout, fragment)
                .addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateNearbyChallenges(challenges: List<Challenge>) {
        this.nearby?.adapter?.setAll(challenges)
        this.nearby?.stopRefreshing()

    }

    override fun updateOngoingChallenges(challenges: List<Challenge>) {
        this.ongoing?.adapter?.setAll(challenges)
        this.ongoing?.stopRefreshing()
    }

    override fun updateAllChallenges(challenges: List<Challenge>) {
        this.all?.adapter?.setAll(challenges)
        this.all?.stopRefreshing()
    }
}
