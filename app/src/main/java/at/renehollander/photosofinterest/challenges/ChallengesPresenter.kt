package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.challenges.domain.model.RequestFilter
import at.renehollander.photosofinterest.challenges.domain.usecase.LoadChallenges
import javax.inject.Inject

class ChallengesPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val loadChallenges: LoadChallenges
) : ChallengesContract.Presenter {

    private var view: ChallengesContract.View? = null

    override fun takeView(view: ChallengesContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun fetchNearbyChallenges() {
        fetchChallenges(RequestFilter.NEARBY)
    }

    override fun fetchOngoingChallenges() {
        fetchChallenges(RequestFilter.ONGOING)
    }

    override fun fetchAllChallenges() {
        fetchChallenges(RequestFilter.ALL)
    }

    private fun fetchChallenges(filter: RequestFilter) {
        this.useCaseHandler.execute(this.loadChallenges, LoadChallenges.RequestValues(filter), { response ->
            when (filter) {
                RequestFilter.NEARBY -> this.view?.updateNearbyChallenges(response.challenges)
                RequestFilter.ONGOING -> this.view?.updateOngoingChallenges(response.challenges)
                RequestFilter.ALL -> this.view?.updateAllChallenges(response.challenges)
            }
        }, {

        })
    }
}