package at.renehollander.photosofinterest.challenge

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsContract
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsFragment
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.feed.FeedFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_challenge.*
import javax.inject.Inject

class ChallengeFragment @Inject constructor() : DaggerFragment(), ChallengeContract.View {

    @Inject
    lateinit var presenter: ChallengeContract.Presenter
    @Inject
    lateinit var challengeDetailsFragment: ChallengeDetailsFragment
    @Inject
    lateinit var feedFragment: FeedFragment

    lateinit var adapter: ChallengeFragmentPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        takePhoto.setOnClickListener { this.presenter.takePhoto() }

        adapter = ChallengeFragmentPagerAdapter(childFragmentManager);
        adapter.context = context!!
        adapter.challengeDetailsFragment = challengeDetailsFragment
        adapter.feedFragment = feedFragment
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
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

    override fun startPhotoTake() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity?.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == REQUEST_IMAGE_CAPTURE) {
            this.presenter.photoTaken(data.extras!!["data"] as Bitmap)
        }
    }

    override fun getDetailsPresenter(): ChallengeDetailsContract.Presenter = challengeDetailsFragment.presenter

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}
