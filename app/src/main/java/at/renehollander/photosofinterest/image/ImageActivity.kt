package at.renehollander.photosofinterest.image

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import at.renehollander.photosofinterest.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class ImageActivity : DaggerAppCompatActivity(), ImageContract.View {

    companion object {
        const val MODE_CREATE = 0
        const val MODE_VIEW = 1
    }

    @Inject
    lateinit var presenter: ImageContract.Presenter

    private val controllerListener = object : BaseControllerListener<ImageInfo>() {
        override fun onFailure(id: String?, throwable: Throwable?) {
            Toast.makeText(this@ImageActivity, getString(R.string.image_cannot_load), Toast.LENGTH_SHORT).show()
            Log.i("DraweeUpdate", "Update failed: " + throwable?.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.presenter.takeView(this)

        // This call performs the initialization
        processIntent()

        image.setTapListener(object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                this@ImageActivity.presenter.onImageClicked()
                return true
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                this@ImageActivity.presenter.onImageClicked()
                return true
            }
        })

        backButton.setOnClickListener {
            this.presenter.onBackButtonClicked()
        }

        confirmButton.setOnClickListener {
            this.presenter.onConfirmButtonClicked()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.dropView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun titleProvided(): Boolean {
        return this.titleEditText.text.isNotBlank()
    }

    override fun showTitleMissingAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.image_please_provide_title)
                .setNeutralButton(R.string.button_ok, { dialog, _ ->
                    dialog.dismiss()
                })
        builder.create().show()
    }

    override fun toggleControls() {
        var new = View.GONE
        if (controlsHeader.visibility == View.GONE) new = View.VISIBLE
        controlsHeader.visibility = new
    }

    override fun enableViewMode() {
        titleLabel.visibility = View.VISIBLE
        titleEditText.visibility = View.INVISIBLE
        confirmButton.visibility = View.INVISIBLE
    }

    override fun enableCreateMode() {
        titleLabel.visibility = View.INVISIBLE
        titleEditText.visibility = View.VISIBLE
        confirmButton.visibility = View.VISIBLE
    }

    override fun updateTitle(title: String) {
        titleLabel.text = title
    }

    override fun updateContent(contentUri: String) {
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(contentUri)
                .setTapToRetryEnabled(true)
                .setControllerListener(this.controllerListener)
                .setOldController(image.controller)
                .build()
        image.controller = controller
    }

    override fun returnResult(canceled: Boolean) {
        val data = Intent()
        if (!canceled) {
            data.putExtra("uri", intent.getStringExtra("uri"))
            data.putExtra("title", titleEditText.text.toString())
        }

        if (!canceled) setResult(Activity.RESULT_OK, data)
        else setResult(Activity.RESULT_CANCELED)

        this.finish()
    }

    private fun processIntent() {
        val mode = intent.getIntExtra("mode", -1)
        var title = intent.getStringExtra("title")
        if (title == null) title = ""
        var uri = intent.getStringExtra("uri")
        if (uri == null) uri = ""

        this.presenter.init(mode, title, uri)
    }
}
