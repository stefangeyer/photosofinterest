package at.renehollander.photosofinterest.challenges.overview

class ChallengeOverviewViewHolderPresenter(
        private val adapter: ChallengeOverviewContract.Adapter
) : ChallengeOverviewContract.Presenter {

    private var view: ChallengeOverviewContract.View? = null
    private var position: Int? = null

    override fun takeView(view: ChallengeOverviewContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun bind() {
        val position = this.position
        if (position != null) {
            val challenge = this.adapter.getItemAt(position)
            this.view?.updateTitle(challenge.title)
            this.view?.updateEnd(challenge.end)
            this.view?.updateLocations(challenge.pois.map { p -> p.name })
            this.view?.updateImage(challenge.image.uri)
        }
    }

    override fun changePosition(position: Int) {
        this.position = position
    }

    override fun onImageClicked() {
        val position = this.position
        if (position != null) {
            val challenge = this.adapter.getItemAt(position)
            this.view?.showImage(challenge.title, challenge.image.uri)
        }
    }

    override fun onDetailsButtonClicked() {
        this.view?.showDetails()
    }

    override fun onUploadsButtonClicked() {
        this.view?.showUploads()
    }


}