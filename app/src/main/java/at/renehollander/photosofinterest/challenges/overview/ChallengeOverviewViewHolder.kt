package at.renehollander.photosofinterest.challenges.overview

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.image.ImageActivity
import com.facebook.drawee.view.SimpleDraweeView
import org.threeten.bp.Duration

class ChallengeOverviewViewHolder(
        private val parentView: View,
        private val adapter: ChallengeOverviewContract.Adapter
) : RecyclerView.ViewHolder(parentView), ChallengeOverviewContract.ViewHolder {

    private val image: SimpleDraweeView = this.parentView.findViewById(R.id.image)
    private val title: TextView = this.parentView.findViewById(R.id.title)
    private val endsIn: TextView = this.parentView.findViewById(R.id.endsIn)
    private val locations: TextView = this.parentView.findViewById(R.id.locations)
    private val detailsButton: Button = this.parentView.findViewById(R.id.detailsButton)
    private val uploadsButton: Button = this.parentView.findViewById(R.id.uploadsButton)

    val presenter: ChallengeOverviewContract.ViewHolderPresenter = ChallengeOverviewViewHolderPresenter(this.adapter)

    init {
        this.presenter.takeView(this)

        this.image.setOnClickListener {
            this.presenter.onImageClicked()
        }

        this.detailsButton.setOnClickListener {
            this.presenter.onDetailsButtonClicked()
        }

        this.uploadsButton.setOnClickListener {
            this.presenter.onUploadsButtonClicked()
        }
    }

    override fun updateImage(uri: String) {
        this.image.setImageURI(uri)
    }

    override fun updateTitle(title: String) {
        this.title.text = title
    }

    @SuppressLint("SetTextI18n")
    override fun updateEnd(between: Duration) {
        endsIn.text = parentView.context.resources?.getQuantityString(R.plurals.endsInHours, between.toHours().toInt(), between.toHours().toInt()) + " " +
                parentView.context.resources?.getQuantityString(R.plurals.endsInMinutes, between.minusHours(between.toHours()).toMinutes().toInt(), between.minusHours(between.toHours()).toMinutes().toInt())
    }

    override fun updateLocations(locations: List<String>) {
        val out = locations.joinToString(separator = ", ")
        this.locations.text = out
    }

    override fun showImage(title: String, uri: String) {
        val context = this.parentView.context
        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra("mode", ImageActivity.MODE_VIEW)
        intent.putExtra("title", title)
        intent.putExtra("uri", uri)

        context.startActivity(intent)
    }

    override fun showDetails() {
    }

    override fun showUploads() {
    }
}