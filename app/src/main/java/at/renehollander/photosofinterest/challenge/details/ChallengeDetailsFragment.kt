package at.renehollander.photosofinterest.challenge.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.GlideApp
import at.renehollander.photosofinterest.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge_details.*
import org.threeten.bp.Duration
import javax.inject.Inject

class ChallengeDetailsFragment @Inject constructor() : DaggerFragment(), ChallengeDetailsContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: ChallengeDetailsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge_details, container, false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map?.onCreate(savedInstanceState)
        map?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.update()
        map?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
        map?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        map?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds(LatLng(46.506763, 9.367204), LatLng(49.204010, 17.178483)), 0))
        for (poi in presenter.getChallenge()!!.pois) {
            map?.addMarker(MarkerOptions().position(LatLng(poi.location.latitude, poi.location.longitude)).title(poi.name))
        }
    }

    override fun updateTitle(title: String) {
        challengeName.text = title
    }

    override fun updateImage(uri: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageReference = storageRef.child(uri)

        GlideApp.with(this)
                .load(imageReference)
                .into(challengeImage)
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
