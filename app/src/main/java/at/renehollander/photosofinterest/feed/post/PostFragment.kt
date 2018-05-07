package at.renehollander.photosofinterest.feed.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.image.ImageActivity
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject

class PostFragment @Inject constructor() : DaggerFragment(), PostContract.View {

    @Inject
    lateinit var presenter: PostContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        image.setOnClickListener {
            this.presenter.onImageClicked()
        }

        detailContainer.setOnClickListener {
            this.presenter.onInformationClicked()
        }

        upvoteButton.setOnClickListener {
            this.presenter.onUpvoteButtonClicked()
        }

        downvoteButton.setOnClickListener {
            this.presenter.onDownvoteButtonClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun init(post: Post) {
        this.presenter.init(post)
    }

    override fun updateTitle(title: String) {
        titleLabel.text = title
    }

    override fun updateChallengeName(name: String) {
        challengeLabel.text = name
    }

    override fun updateLocationName(name: String) {
        locationLabel.text = name
    }

    override fun updateUserImage(uri: String) {
        userImage.setImageURI(uri)
    }

    override fun updateImage(uri: String) {
        image.setImageURI(uri)
    }

    override fun updateUpvotes(upvotes: Int) {
        upvoteLabel.text = upvotes.toString()
    }

    override fun updateDownvotes(downvotes: Int) {
        downvoteLabel.text = downvotes.toString()
    }

    override fun showChallengeDetails() {
        // TODO implement
        Toast.makeText(activity, "Show challenge details", Toast.LENGTH_SHORT).show()
        Log.d("Post", "Missing challenge details")
    }

    override fun enableVoteButtons(enabled: Boolean) {
        upvoteButton.isEnabled = enabled
        downvoteButton.isEnabled = enabled
    }

    override fun showImageDetails(title: String, uri: String) {
        val intent = Intent(activity, ImageActivity::class.java)
        intent.putExtra("mode", ImageActivity.MODE_VIEW)
        intent.putExtra("title", title)
        intent.putExtra("uri", uri)
        startActivity(intent)
    }
}
