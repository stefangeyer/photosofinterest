package at.renehollander.photosofinterest.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import com.google.firebase.firestore.GeoPoint
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class ProfileFragment @Inject constructor() : DaggerFragment(), ProfileContract.View {

    @Inject
    lateinit var presenter: ProfileContract.Presenter

    @Inject
    lateinit var postFragment: PostFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    lateinit var user: User

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val now = LocalDateTime.now()
        val challenge1 = Challenge(
                title = "Challenge 1",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Poertschach_von_Gloriette_04.jpg/1920px-Poertschach_von_Gloriette_04.jpg",
                start = now.minusDays(4), end = now.plusDays(3), description = "Desc 123456", regions = listOf(Region("Some Region", getPoints())), pois = getPois())
        user = User(email = "user1@example.com", name = "User 1", image = Image("http://tal.am/bc/wm.php?id=tal-ami-profile-1"))
        val image1 = Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg")
        val post1 = Post(user = user, challenge = challenge1, title = "Some Post Title", image = image1, upvotes = 10, downvotes = 5, origin = getPoints()[0], poi = getPois()[0])

        presenter.setUser(user)

        this.postFragment.setOnDataReloadListener(object : PostContract.View.OnDataReloadListener {
            override fun onReload() {
                this@ProfileFragment.postFragment.adapter.setAll(listOf(
                        post1
                ))
                this@ProfileFragment.postFragment.stopRefreshing()
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

    private fun getPoints() = listOf(GeoPoint(0.1, 23.7), GeoPoint(45.2, 99.9), GeoPoint(55.8, 12.789))

    private fun getPois() = listOf(
            PointOfInterest(name = "POI 1", location = getPoints()[0], radius = 50),
            PointOfInterest(name = "POI 2", location = getPoints()[1], radius = 30),
            PointOfInterest(name = "POI 3", location = getPoints()[2], radius = 66))
}
