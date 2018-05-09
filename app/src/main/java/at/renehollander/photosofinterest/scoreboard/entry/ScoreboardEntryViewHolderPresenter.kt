package at.renehollander.photosofinterest.scoreboard.entry

class ScoreboardEntryViewHolderPresenter(
        private val adapter: ScoreboardEntryContract.Adapter
) : ScoreboardEntryContract.ViewHolderPresenter {

    private var view: ScoreboardEntryContract.ViewHolder? = null
    var position: Int? = null

    override fun takeView(view: ScoreboardEntryContract.ViewHolder) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

}
