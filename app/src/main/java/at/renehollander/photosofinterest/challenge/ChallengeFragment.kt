package at.renehollander.photosofinterest.challenge

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
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
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import at.renehollander.photosofinterest.image.ImageActivity
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

    private var uri: Uri? = null
    private var initialDetailMode = true

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
            activity?.startActivityForResult(intent, REQUEST_IMAGE_TITLE)
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.d("ChallengeFragment", uri.toString())
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("mode", ImageActivity.MODE_CREATE)
            intent.putExtra("uri", uri.toString())
            activity?.startActivityForResult(intent, REQUEST_IMAGE_TITLE)
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_TITLE) {
            val title = data?.getStringExtra("title")
            val uri = data?.getStringExtra("uri")
        }
    }

    override fun startPhotoTake() {
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

    override fun onPostCreation(post: Post) {
        Toast.makeText(context, "Post was created!", Toast.LENGTH_SHORT).show()
    }

    override fun onPostCreationFailed() {
        Toast.makeText(context, "Post could not be created!", Toast.LENGTH_SHORT).show()
    }

    override fun showCannotReload() {
        Toast.makeText(activity, getString(R.string.feed_cannot_reload), Toast.LENGTH_SHORT).show()
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

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_SELECT = 2
        const val REQUEST_IMAGE_TITLE = 3
    }
}
