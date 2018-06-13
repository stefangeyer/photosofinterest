package at.renehollander.photosofinterest.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import at.renehollander.photosofinterest.data.source.PostDataRepository
import at.renehollander.photosofinterest.data.source.UserManager
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment @Inject constructor() : DaggerFragment(), ProfileContract.View {

    @Inject
    lateinit var presenter: ProfileContract.Presenter

    @Inject
    lateinit var postFragment: PostFragment

    @Inject
    lateinit var postDataRepository: PostDataRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    lateinit var user: User

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        user = UserManager.getInstance().getCurrentUser()!!
        presenter.setUser(user)

        this.postFragment.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                postDataRepository.loadPosts(user, object : LoadRecordCallback<Post> {
                    override fun onRecordsLoaded(records: List<Post>) {
                        this@ProfileFragment.postFragment.adapter.setAll(records)
                        this@ProfileFragment.postFragment.stopRefreshing()
                    }

                    override fun onDataNotAvailable() {
                        this@ProfileFragment.postFragment.stopRefreshing()
                        Log.d(LoadPosts.TAG, "Fetching challenges did not produce any data")
                    }
                })
            }
        })

        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.frameLayout, this.postFragment)
        ft?.commit()
    }

    override fun updateProfileImage(uri: String) {
        imageDraweeView.setImageURI(uri)
    }

    override fun updateName(name: String) {
        nameTextView.text = name
    }

    override fun updateScore(score: Int) {
        scoreTextView.text = score.toString()
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

}
