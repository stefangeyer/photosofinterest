package at.renehollander.photosofinterest.challenge

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.auth.AuthActivity
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsContract
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsFragment
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.PostDataRepository
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import at.renehollander.photosofinterest.image.ImageActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge.*
import java.io.File
import javax.inject.Inject

class ChallengeFragment @Inject constructor() : DaggerFragment(), ChallengeContract.View {

    @Inject
    lateinit var presenter: ChallengeContract.Presenter
    @Inject
    lateinit var challengeDetailsFragment: ChallengeDetailsFragment
    @Inject
    lateinit var postFragment: PostFragment

    lateinit var adapter: ChallengeFragmentPagerAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var uri: Uri? = null
    private var initialDetailMode = true
    private var currentDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        takePhoto.setOnClickListener { this.presenter.takePhoto() }

        this.postFragment.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                this@ChallengeFragment.presenter.loadChallengePosts()
            }
        })

        adapter = ChallengeFragmentPagerAdapter(childFragmentManager)
        adapter.context = context!!
        adapter.challengeDetailsFragment = challengeDetailsFragment
        adapter.postFragment = postFragment
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(if (this.initialDetailMode) 0 else 1)?.select()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.update()
    }

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_SELECT) {
            Log.d("ChallengeFragment", data!!.toUri(0))
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("mode", ImageActivity.MODE_CREATE)
            intent.putExtra("uri", data.toUri(0))
            startActivityForResult(intent, REQUEST_IMAGE_TITLE)
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.d("ChallengeFragment", uri.toString())
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("mode", ImageActivity.MODE_CREATE)
            intent.putExtra("uri", uri.toString())
            startActivityForResult(intent, REQUEST_IMAGE_TITLE)
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_TITLE) {
            val title = data?.getStringExtra("title")!!
            val uri = data.getStringExtra("uri")!!
            if (checkLocationPermission()) {
                showProgress()
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    presenter.createPost(title, uri, GeoPoint(it.latitude, it.longitude))
                }
            }

        }
    }

    override fun startPhotoTake() {
        if (!checkLocationPermission()) return
        val smBuilder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(smBuilder.build())

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Upload Picture")
        builder.setIcon(R.drawable.ic_photo_camera_black_24dp)
        builder.setMessage("Take a picture with your phone camera or upload an existing")
        builder.setPositiveButton("Take Picture", { _, _ ->
            if (checkWriteExternalPermission()) {
                val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File.createTempFile("poi_upload_" + System.currentTimeMillis(), ".jpg", storageDir)
                uri = Uri.fromFile(image)
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                if (takePictureIntent.resolveActivity(activity?.packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        })
        builder.setNegativeButton("Select Picture", { _, _ ->
            val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"
            val chooserIntent = Intent.createChooser(pickIntent, "Select Image")
            if (chooserIntent.resolveActivity(activity?.packageManager) != null) {
                startActivityForResult(chooserIntent, REQUEST_IMAGE_SELECT)
            }
        })
        builder.setNeutralButton("Cancel", { _, _ ->
        })
        builder.create().show()
    }

    override fun startLogin() {
        val intent = Intent(context, AuthActivity::class.java)
        startActivity(intent)
    }

    override fun updateChallengePosts(posts: List<Post>) {
        this.adapter.postFragment.adapter.setAll(posts)
        this.adapter.postFragment.stopRefreshing()
    }

    override fun showDetails() {
        if (tabLayout != null) {
            tabLayout.getTabAt(0)?.select()
        }
        this.initialDetailMode = true
    }

    override fun showUploads() {
        if (tabLayout != null) {
            tabLayout.getTabAt(1)?.select()
        }
        this.initialDetailMode = false
    }

    private fun showProgress() {
        currentDialog = AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.post_uploading))
                .create().apply { show() }
    }

    private fun showMessage(message: String) {
        currentDialog = android.app.AlertDialog.Builder(context!!).setTitle(getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, { d, _ -> d.dismiss() }).create().apply { show() }
    }

    private fun dismissDialog() {
        if (currentDialog != null && currentDialog!!.isShowing) {
            currentDialog!!.cancel()
        }
    }

    override fun onPostCreation(post: Post) {
        dismissDialog()
        showMessage(getString(R.string.post_created))
    }

    override fun onPostCreationFailed() {
        dismissDialog()
        showMessage(getString(R.string.post_cannot_create))
    }

    override fun showCannotReload() {
        Toast.makeText(activity, getString(R.string.post_cannot_reload), Toast.LENGTH_SHORT).show()
    }

    override fun getDetailsPresenter(): ChallengeDetailsContract.Presenter = challengeDetailsFragment.presenter

    private fun checkWriteExternalPermission(): Boolean {
        val permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val res = context!!.checkCallingOrSelfPermission(permission)
        if (res != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            return false
        } else {
            return true
        }
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

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_SELECT = 2
        const val REQUEST_IMAGE_TITLE = 3
    }

}
