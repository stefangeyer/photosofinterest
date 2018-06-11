package at.renehollander.photosofinterest.feed.post

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post

class PostAdapter constructor(private val application: PhotosOfInterest) : RecyclerView.Adapter<PostViewHolder>(), PostContract.Adapter {

    override val itemList: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(item, this)
    }

    override fun getItemCount(): Int = this.itemList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.updateViewHolderPosition(position)
        holder.bind()
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }
}