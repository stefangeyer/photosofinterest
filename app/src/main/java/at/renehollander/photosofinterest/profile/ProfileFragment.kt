package at.renehollander.photosofinterest.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.feed.post.PostContract
import at.renehollander.photosofinterest.feed.post.PostFragment
import dagger.android.support.DaggerFragment
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val now = LocalDateTime.now()
        val challenge1 = Challenge(
                title = "Challenge 1",
                image = Image("https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Poertschach_von_Gloriette_04.jpg/1920px-Poertschach_von_Gloriette_04.jpg"),
                start = now.minusDays(4), end = now.plusDays(3), description = "Desc 123456", regions = listOf(Region("Some Region", getPoints())), pois = getPois())
        val user1 = User("user1@example.com", "User 1", Image("http://tal.am/bc/wm.php?id=tal-ami-profile-1"))
        val image1 = Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg")
        val post1 = Post(user1, challenge1, "Some Post Title", image1, 10, 5, getPoints()[0], getPois()[0])

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

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    private fun getPoints() = listOf(Point(0.1, 23.7), Point(45.2, 99.9), Point(55.8, 12.789))

    private fun getPois() = listOf(
            PointOfInterest("POI 1", getPoints()[0], 50),
            PointOfInterest("POI 2", getPoints()[1], 30),
            PointOfInterest("POI 3", getPoints()[2], 66))
}
