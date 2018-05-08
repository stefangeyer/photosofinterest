package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.ScoreboardEntry
import at.renehollander.photosofinterest.data.User
import javax.inject.Inject

class ScoreboardPresenter @Inject constructor(
) : ScoreboardContract.Presenter {
    override fun fetchScores() {
        this.view?.updateScores(mutableListOf(
                ScoreboardEntry(null, User("user1@example.com", "User 1", Image("img1")), 20),
                ScoreboardEntry(null, User("user2@example.com", "User 2", Image("img2")), 30),
                ScoreboardEntry(null, User("user3@example.com", "User 3", Image("img2")), 100)
        ))
    }

    private var view: ScoreboardContract.View? = null

    override fun takeView(view: ScoreboardContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}
