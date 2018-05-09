package at.renehollander.photosofinterest.challenge.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import dagger.android.support.DaggerFragment
import org.threeten.bp.Duration
import javax.inject.Inject

class ChallengeDetailsFragment @Inject constructor() : DaggerFragment(), ChallengeDetailsContract.View {

    @Inject
    lateinit var presenter: ChallengeDetailsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pointsImage.setImageURI("https://i.imgur.com/DgtBZ37.png")
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateTitle(title: String) {
        challengeName.text = title
    }

    override fun updateImage(uri: String) {
        challengeImage.setImageURI(uri)
    }

    @SuppressLint("SetTextI18n")
    override fun updateEndTime(between: Duration) {
        challengeEndTime.text = context?.resources?.getQuantityString(R.plurals.endsInHours, between.toHours().toInt(), between.toHours().toInt()) + " " +
                context?.resources?.getQuantityString(R.plurals.endsInMinutes, between.minusHours(between.toHours()).toMinutes().toInt(), between.minusHours(between.toHours()).toMinutes().toInt())
    }

    override fun updateRegion(region: List<String>) {
        challengePlace.text = region.joinToString(", ")
    }

    override fun updateDescription(description: String) {
        challengeDescription.text = description
    }

}
