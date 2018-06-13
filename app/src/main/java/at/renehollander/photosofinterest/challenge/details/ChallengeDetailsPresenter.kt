package at.renehollander.photosofinterest.challenge.details

import android.util.Log
import at.renehollander.photosofinterest.challenges.domain.usecase.LoadChallenges
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.source.ChallengeDataRepository
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class ChallengeDetailsPresenter @Inject constructor(
        private val challengesRepository: ChallengeDataRepository
) : ChallengeDetailsContract.Presenter {

    private var view: ChallengeDetailsContract.View? = null
    private var challenge: Challenge? = null

    override fun takeView(view: ChallengeDetailsContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun setChallenge(challenge: Challenge?) {
        this.challenge = challenge
        if (challenge?.pois?.isEmpty()!!) {
            challengesRepository.loadChallengeDetails(challenge, object : GetRecordCallback<Challenge> {
                override fun onRecordLoaded(record: Challenge) {
                    this@ChallengeDetailsPresenter.challenge = challenge
                    this@ChallengeDetailsPresenter.update()
                }

                override fun onDataNotAvailable() {
                    Log.d(LoadChallenges.TAG, "Fetching challenge did not produce any data")
                }
            })
        }

        // DO NOT PERFORM AN UI UPDATE HERE
    }

    override fun getChallenge(): Challenge? {
        return challenge
    }

    override fun update() {
        if (this.challenge != null) {
            this.view?.updateTitle(challenge!!.title)
            this.view?.updateDescription(challenge!!.description)
            this.view?.updateImage(challenge!!.image)
            this.view?.updateEndTime(Duration.between(LocalDateTime.now(), challenge!!.end))
            this.view?.updateRegion(challenge!!.regions.map { region -> region.description })
            this.view?.updateMarkers(challenge!!.pois)
        }
    }
}
