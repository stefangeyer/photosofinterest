package at.renehollander.photosofinterest.feed.post

class PostViewHolderPresenter(
        private val postAdapterPresenter: PostContract.AdapterPresenter
) : PostContract.ViewHolderPresenter {

    private var view: PostContract.ViewHolder? = null
    var position: Int? = null

    override fun takeView(view: PostContract.ViewHolder) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun onImageClicked() {
        if (position == null) return
        val post = this.postAdapterPresenter.getItemAt(position!!)
        this.view?.showImageDetails(post.title, post.image.uri)
    }

    override fun onInformationClicked() {
        this.view?.showChallengeDetails()
    }

    override fun onUpvoteButtonClicked() {
        // TODO vote use case
        if (position == null) return
        val post = this.postAdapterPresenter.getItemAt(position!!)
        post.upvotes++
        this.view?.updateUpvotes(post.upvotes)
    }

    override fun onDownvoteButtonClicked() {
        // TODO vote use case
        if (position == null) return
        val post = this.postAdapterPresenter.getItemAt(position!!)
        post.downvotes++
        this.view?.updateUpvotes(post.downvotes)
    }
}