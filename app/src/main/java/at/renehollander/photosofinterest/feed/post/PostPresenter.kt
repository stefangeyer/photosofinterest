package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.data.Post
import javax.inject.Inject

class PostPresenter @Inject constructor() : PostContract.Presenter {

    private var view: PostContract.View? = null

    override fun takeView(view: PostContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun init(post: Post) {
        this.view?.updateTitle(post.title)
        this.view?.updateChallengeName(post.challenge.title)
        this.view?.updateLocationName(post.poi.name)
        this.view?.updateImage(post.image.uri)
        this.view?.updateUserImage(post.user.image.uri)
    }

    override fun onInformationClicked() {
        this.view?.showChallengeDetails()
    }

    override fun onUpvoteButtonClicked() {
        // TODO vote use case
        this.view?.increaseUpvotes()
    }

    override fun onDownvoteButtonClicked() {
        // TODO vote use case
        this.view?.increaseDownvotes()
    }
}