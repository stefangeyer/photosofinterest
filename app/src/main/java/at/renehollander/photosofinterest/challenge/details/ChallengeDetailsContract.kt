package at.renehollander.photosofinterest.challenge.details

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Challenge
import org.threeten.bp.Duration

/**
 * Contract for the feed parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengeDetailsContract {
    interface View : BaseView {
        fun updateTitle(title: String)
        fun updateImage(uri: String)
        fun updateEndTime(between: Duration)
        fun updateRegion(region: List<String>)
        fun updateDescription(description: String)
    }

    interface Presenter : BasePresenter<View> {
        fun setChallenge(challenge: Challenge?)
        fun getChallenge(): Challenge?
        fun update()
    }
}
