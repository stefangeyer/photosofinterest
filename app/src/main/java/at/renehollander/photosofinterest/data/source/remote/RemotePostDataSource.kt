package at.renehollander.photosofinterest.data.source.remote

import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import at.renehollander.photosofinterest.data.source.PostDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@ApplicationScoped
class RemotePostDataSource @Inject constructor() : PostDataSource {

    override fun loadPosts(filter: PostDataSource.Filter, callback: LoadRecordCallback<Post>) {
        val now = LocalDateTime.now()
        val challenge1 = Challenge(
                title = "Challenge 1",
                image = Image("https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Poertschach_von_Gloriette_04.jpg/1920px-Poertschach_von_Gloriette_04.jpg"),
                start = now.minusDays(4), end = now.plusDays(3), description = "Desc 123456", regions = listOf(Region("Some Region", getPoints())), pois = getPois())
        val user1 = User("user1@example.com", "User 1", Image("http://tal.am/bc/wm.php?id=tal-ami-profile-1"))
        val user2 = User("user2@example.com", "User 2", Image("http://www.christopherxjjensen.com/wp-content/gallery/profile-pics/thumbs/thumbs_Profile-Pic_2014-09-07_1000px.jpg"))
        val image1 = Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg")
        val image2 = Image("https://webheimat.at/aktiv/Urlaub/Tipps/Woerthersee-Sommer-Events/Woerthersee-Sommer_high.jpg")
        val post1 = Post(user1, challenge1, "Some Post Title", image1, 10, 5, getPoints()[0], getPois()[0])
        val post2 = Post(user1, challenge1, "Another", image1, 100, 5, getPoints()[2], getPois()[2])
        val post3 = Post(user2, challenge1, "Another one", image2, 0, 5, getPoints()[1], getPois()[1])
        val post4 = Post(user2, challenge1, "Another one", image2, 0, 50, getPoints()[2], getPois()[2])
        val posts: List<Post> = when (filter) {
            PostDataSource.Filter.RISING -> {
                listOf(post3, post1)
            }
            PostDataSource.Filter.RECENT -> {
                listOf(post4, post2)
            }
            PostDataSource.Filter.TOP -> {
                listOf(post2, post1)
            }
        }

        callback.onRecordsLoaded(posts)
    }

    private fun getPoints() = listOf(Point(0.1, 23.7), Point(45.2, 99.9), Point(55.8, 12.789))

    private fun getPois() = listOf(
            PointOfInterest("POI 1", getPoints()[0], 50),
            PointOfInterest("POI 2", getPoints()[1], 30),
            PointOfInterest("POI 3", getPoints()[2], 66))
}