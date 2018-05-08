package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.data.Post

class PostAdapterPresenter : PostContract.AdapterPresenter {

    private var view: PostContract.AdapterView? = null
    private var posts = mutableListOf<Post>()

    override fun takeView(view: PostContract.AdapterView) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun onDataChange(posts: List<Post>) {
        posts.forEach {
            if(!this.posts.contains(it)){
                this.posts.add(it)
            }
        }
        view?.notifyAdapter()
    }

    override fun getItemCount(): Int = this.posts.size

    override fun getItemAt(position: Int): Post = this.posts[position]
}