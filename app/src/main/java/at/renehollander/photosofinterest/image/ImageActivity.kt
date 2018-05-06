package at.renehollander.photosofinterest.image

import android.os.Bundle
import at.renehollander.photosofinterest.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : DaggerAppCompatActivity(), ImageContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        image.setImageURI("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png")
    }

    override fun toggleControls() {

    }
}
