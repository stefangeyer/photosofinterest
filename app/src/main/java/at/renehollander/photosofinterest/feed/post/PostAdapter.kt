package at.renehollander.photosofinterest.feed.post

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post

class PostAdapter constructor(private val application: PhotosOfInterest) : RecyclerView.Adapter<PostViewHolder>(), PostContract.Adapter {

    private var posts = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(item, this, application)
    }

    override fun getItemCount(): Int = this.posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.updateViewHolderPosition(position)
        holder.bind()
    }

    override fun setAll(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyAdapter()
    }

    override fun addItem(post: Post) {
        this.posts.add(post)
        notifyAdapter()
    }

    override fun removeItem(post: Post) {
        this.posts.remove(post)
        notifyAdapter()
    }

    override fun getItemAt(position: Int): Post = this.posts[position]

    override fun getItems(): List<Post> = this.posts

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }
}