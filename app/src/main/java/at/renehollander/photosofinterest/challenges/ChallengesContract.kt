package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.Challenge

/**
 * Contract for the challenges parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface ChallengesContract {
    interface View : BaseView {
        fun updateNearbyChallenges(challenges: List<Challenge>)
        fun updateOngoingChallenges(challenges: List<Challenge>)
        fun updateAllChallenges(challenges: List<Challenge>)
    }
    interface Presenter : BasePresenter<View> {
        fun fetchNearbyChallenges()
        fun fetchOngoingChallenges()
        fun fetchAllChallenges()
    }
}