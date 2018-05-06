package at.renehollander.photosofinterest.image

import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_image.*
import javax.inject.Inject

class ImageActivity : DaggerAppCompatActivity(), ImageContract.View {

    @Inject
    lateinit var presenter: ImageContract.Presenter

    private val controllerListener = object : BaseControllerListener<ImageInfo>() {
        override fun onFailure(id: String?, throwable: Throwable?) {
            // TODO string resource
            Toast.makeText(this@ImageActivity, "Cannot load image", Toast.LENGTH_SHORT).show()
            Log.i("DraweeUpdate", throwable?.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        this.presenter.takeView(this)
        this.presenter.init()

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

    override fun returnResult() {
        this.finish()
    }
}
