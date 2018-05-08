package at.renehollander.photosofinterest.feed.post

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post

class PostAdapter(posts: List<Post>): RecyclerView.Adapter<PostViewHolder>(), PostContract.AdapterView {

    private val presenter: PostContract.AdapterPresenter = PostAdapterPresenter()

    init {
        update(posts)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.presenter.takeView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(item, this.presenter)
    }

    override fun getItemCount(): Int = this.presenter.getItemCount()

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = this.presenter.getItemAt(position)

        holder.title.text = post.title
        holder.challenge.text = post.challenge.title
        holder.location.text = post.poi.name
        holder.upvotes.text = post.upvotes.toString()
        holder.downvotes.text = post.downvotes.toString()
        holder.image.setImageURI(post.image.uri)
        holder.userImage.setImageURI(post.user.image.uri)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.presenter.dropView()
    }

    override fun update(posts: List<Post>) {
        this.presenter.onDataChange(posts)
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }
}