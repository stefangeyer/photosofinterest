package at.renehollander.photosofinterest.challenge.details

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.GlideApp
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.PointOfInterest
import com.github.aakira.expandablelayout.ExpandableLayout
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge_details.*
import org.threeten.bp.Duration
import javax.inject.Inject


class ChallengeDetailsFragment @Inject constructor() : DaggerFragment(), ChallengeDetailsContract.View, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    @Inject
    lateinit var presenter: ChallengeDetailsContract.Presenter

    private var googleMap: GoogleMap? = null
    private var markers: MutableList<Marker> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map?.onCreate(savedInstanceState)
        map?.getMapAsync(this)

        descriptionHeader.setOnClickListener { onClickButton(descriptionExpandableLayout) }

        mapHeader.setOnClickListener { onClickButton(mapExpandableLayout) }

        descriptionExpandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {
            override fun onPreOpen() {
                createRotateAnimator(descriptionExpanded, 0f, 180f).start()
            }

            override fun onPreClose() {
                createRotateAnimator(descriptionExpanded, 180f, 0f).start()
            }
        })

        mapExpandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {
            override fun onPreOpen() {
                createRotateAnimator(mapExpanded, 0f, 180f).start()
            }

            override fun onPreClose() {
                createRotateAnimator(mapExpanded, 180f, 0f).start()
            }
        })
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
        googleMap = map
        googleMap?.setOnMapLoadedCallback(this)
        if (checkLocationPermission()) {
            googleMap?.isMyLocationEnabled = true
        }
    }

    override fun onMapLoaded() {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds(LatLng(46.506763, 9.367204), LatLng(49.204010, 17.178483)), 0))
        presenter.update()
    }

    override fun updateTitle(title: String) {
        challengeName.text = title
    }

    override fun updateImage(uri: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageReference = storageRef.child(uri)

        GlideApp.with(this)
                .load(imageReference)
                .centerCrop()
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

    private fun onClickButton(expandableLayout: ExpandableLayout) {
        expandableLayout.toggle()
    }

    override fun updateMarkers(pois: List<PointOfInterest>) {
        markers.forEach { it.remove() }
        if (googleMap != null) {
            for (poi in pois) {
                markers.add(googleMap!!.addMarker(MarkerOptions().position(LatLng(poi.location.latitude, poi.location.longitude)).title(poi.name)))
            }
        }
    }

    fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)
        return animator
    }

    private fun checkLocationPermission(): Boolean {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        val res = context!!.checkCallingOrSelfPermission(permission)
        if (res != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return false
        } else {
            return true
        }
    }
}
