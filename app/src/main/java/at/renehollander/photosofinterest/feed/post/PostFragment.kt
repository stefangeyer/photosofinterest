package at.renehollander.photosofinterest.feed.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.R
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

    override fun increaseUpvotes() {
        val curr = upvoteLabel.text.toString().toInt()
        upvoteLabel.text = (curr + 1).toString()
    }

    override fun updateDownvotes(downvotes: Int) {
        downvoteLabel.text = downvotes.toString()
    }

    override fun increaseDownvotes() {
        val curr = downvoteLabel.text.toString().toInt()
        downvoteLabel.text = (curr + 1).toString()
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
}
