package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.data.Post

class PostViewHolderPresenter(
        private val adapter: PostContract.Adapter
) : PostContract.ViewHolderPresenter {

    private var view: PostContract.ViewHolder? = null
    private var position: Int? = null

    override fun takeView(view: PostContract.ViewHolder) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun onImageClicked() {
        if (position == null) return
        val post = this.adapter.getItemAt(position!!)
        this.view?.showImageDetails(post.title, post.image.uri)
    }

    override fun onInformationClicked() {
        this.view?.showChallengeDetails()
    }

    override fun onUpvoteButtonClicked() {
        // TODO vote use case
        if (position == null) return
        val post = this.adapter.getItemAt(position!!)
        post.upvotes++
        updateVotes(post)
    }

    override fun onDownvoteButtonClicked() {
        // TODO vote use case
        if (position == null) return
        val post = this.adapter.getItemAt(position!!)
        post.downvotes++
        updateVotes(post)
    }

    override fun onPositionChanged(position: Int) {
        this.position = position
    }

    private fun updateVotes(post: Post) {
        this.view?.updateVotes(post.upvotes - post.downvotes)
    }

    override fun onBind() {
        val position = this.position
        if (position != null) {
            val post = adapter.getItemAt(position)
            this.view?.updateTitle(post.title)
            this.view?.updateChallengeName(post.challenge.title)
            this.view?.updateLocationName(post.poi.name)
            updateVotes(post)
            this.view?.updateImage(post.image.uri)
            this.view?.updateUserImage(post.user.image.uri)
        }
    }
}