package at.renehollander.photosofinterest.challenge.details

import at.renehollander.photosofinterest.data.Challenge
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class ChallengeDetailsPresenter @Inject constructor(
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
        this.update()
    }

    override fun update() {
        if (this.challenge != null) {
            this.view?.updateTitle(challenge!!.title)
            this.view?.updateDescription(challenge!!.description)
            this.view?.updateImage(challenge!!.image)
            this.view?.updateEndTime(Duration.between(LocalDateTime.now(), challenge!!.end))
            this.view?.updateRegion(challenge!!.regions.map { region -> region.description })
        }
    }
}
