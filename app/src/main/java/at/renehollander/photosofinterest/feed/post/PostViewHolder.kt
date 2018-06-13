package at.renehollander.photosofinterest.feed.post

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import at.renehollander.photosofinterest.GlideApp
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.image.ImageActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.google.firebase.storage.FirebaseStorage

class PostViewHolder(
        private val parentView: View,
        private val adapter: PostContract.Adapter
) : RecyclerView.ViewHolder(parentView), PostContract.ViewHolder {

    val title: TextView = this.parentView.findViewById(R.id.titleLabel)
    val challenge: TextView = this.parentView.findViewById(R.id.challengeLabel)
    val location: TextView = this.parentView.findViewById(R.id.locationLabel)
    val voteLabel: TextView = this.parentView.findViewById(R.id.voteLabel)
    val upvoteButton: ImageView = this.parentView.findViewById(R.id.upvoteButton)
    val downvoteButton: ImageView = this.parentView.findViewById(R.id.downvoteButton)
    val image: SimpleDraweeView = this.parentView.findViewById(R.id.image)
    val userImage: SimpleDraweeView = this.parentView.findViewById(R.id.userImage)
    val detailContainer: View = this.parentView.findViewById(R.id.detailContainer)

    val presenter = PostViewHolderPresenter(this.adapter)

    init {
        this.presenter.takeView(this)

        this.image.setOnClickListener {
            this.presenter.onImageClicked()
        }

        this.detailContainer.setOnClickListener {
            this.presenter.onInformationClicked()
        }

        this.upvoteButton.setOnClickListener {
            this.presenter.onUpvoteButtonClicked()
        }

        this.downvoteButton.setOnClickListener {
            this.presenter.onDownvoteButtonClicked()
        }
    }

    override fun bind() {
        this.presenter.onBind()
    }

    override fun updateTitle(title: String) {
        this.title.text = title
    }

    override fun updateChallengeName(name: String) {
        this.challenge.text = name
    }

    override fun updateLocationName(name: String) {
        this.location.text = name
    }

    override fun updateUserImage(uri: String) {
        this.userImage.setImageURI(uri)
    }

    override fun updateImage(uri: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageReference = storageRef.child(uri)

        GlideApp.with(this.parentView)
                .load(imageReference)
                .centerCrop()
                .into(image)
    }

    override fun updateVotes(votes: Int) {
        this.voteLabel.text = votes.toString()
    }

    override fun showChallengeDetails() {
        // TODO implement
        Toast.makeText(this.parentView.context, "Show challenge details", Toast.LENGTH_SHORT).show()
        Log.d("Post", "Missing challenge details")
    }

    override fun enableVoteButtons(enabled: Boolean) {
        this.upvoteButton.isEnabled = enabled
        this.downvoteButton.isEnabled = enabled
    }

    override fun showImageDetails(title: String, uri: String) {
        val context = this.parentView.context
        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra("mode", ImageActivity.MODE_VIEW)
        intent.putExtra("title", title)
        intent.putExtra("uri", uri)
        context.startActivity(intent)
    }

    override fun updateViewHolderPosition(position: Int) {
        this.presenter.onPositionChanged(position)
    }
}