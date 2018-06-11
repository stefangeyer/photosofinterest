package at.renehollander.photosofinterest.challenges

import android.util.Log
import at.renehollander.photosofinterest.challenges.domain.usecase.LoadChallenges
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.source.ChallengeDataRepository
import at.renehollander.photosofinterest.data.source.ChallengeDataSource
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import javax.inject.Inject

class ChallengesPresenter @Inject constructor(
        private val challengesRepository: ChallengeDataRepository
) : ChallengesContract.Presenter {

    private var view: ChallengesContract.View? = null

    override fun takeView(view: ChallengesContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun fetchNearbyChallenges() {
        fetchChallenges(ChallengeDataSource.Filter.NEARBY)
    }

    override fun fetchOngoingChallenges() {
        fetchChallenges(ChallengeDataSource.Filter.ONGOING)
    }

    override fun fetchAllChallenges() {
        fetchChallenges(ChallengeDataSource.Filter.ALL)
    }

    private fun fetchChallenges(filter: ChallengeDataSource.Filter) {
        challengesRepository.loadChallenges(filter, object : LoadRecordCallback<Challenge> {
            override fun onRecordsLoaded(records: List<Challenge>) {
                when (filter) {
                    ChallengeDataSource.Filter.NEARBY -> view?.updateNearbyChallenges(records)
                    ChallengeDataSource.Filter.ONGOING -> view?.updateOngoingChallenges(records)
                    ChallengeDataSource.Filter.ALL -> view?.updateAllChallenges(records)
                }
            }

            override fun onDataNotAvailable() {
                Log.d(LoadChallenges.TAG, "Fetching challenges did not produce any data")
            }
        })

//        this.useCaseHandler.execute(this.loadChallenges, LoadChallenges.RequestValues(filter), { response ->
//            when (filter) {
//                RequestFilter.NEARBY -> this.view?.updateNearbyChallenges(response.challenges)
//                RequestFilter.ONGOING -> this.view?.updateOngoingChallenges(response.challenges)
//                RequestFilter.ALL -> this.view?.updateAllChallenges(response.challenges)
//            }
//        }, {
//
//        })
    }
}