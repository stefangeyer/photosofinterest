package at.renehollander.photosofinterest.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.auth.AuthActivity
import at.renehollander.photosofinterest.challenge.ChallengeFragment
import at.renehollander.photosofinterest.challenges.ChallengesFragment
import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.feed.FeedFragment
import at.renehollander.photosofinterest.profile.ProfileFragment
import at.renehollander.photosofinterest.scoreboard.ScoreboardFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    @Inject
    lateinit var challengesFragment: ChallengesFragment
    @Inject
    lateinit var feedFragment: FeedFragment
    @Inject
    lateinit var profileFragment: ProfileFragment
    @Inject
    lateinit var scoreboardFragment: ScoreboardFragment
    @Inject
    lateinit var challengeFragment: ChallengeFragment
    @Inject
    lateinit var application: PhotosOfInterest

    private lateinit var signIn: MenuItem
    private lateinit var signOut: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val selectedFragment: Fragment
        when (item.itemId) {
            R.id.navigation_feed -> {
                selectedFragment = this.feedFragment
            }
            R.id.navigation_challenges -> {
                selectedFragment = this.challengesFragment
            }
            R.id.navigation_scoreboard -> {
                selectedFragment = this.scoreboardFragment
            }
            R.id.navigation_profile -> {
                selectedFragment = this.profileFragment
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, selectedFragment)
        transaction.commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        challengeFragment.presenter.setChallenge(Challenge(
                "Challenge 1",
                Image("http://ferienstar.de/wp-content/uploads/2017/02/sieghart-reisen-woerthersee.jpg"),
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(2),
                "This is the very awesome first challenge!",
                mutableListOf(Region("Carinthia", mutableListOf()), Region("Lower Austria", mutableListOf())),
                mutableListOf(PointOfInterest("Point 1", Point(10.0, 10.0), 20))))

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, this.feedFragment)
        transaction.commit()

        this.presenter.takeView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        signIn = menu.findItem(R.id.sign_in)
        signOut = menu.findItem(R.id.sign_out)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sign_in -> presenter.signIn()
            R.id.sign_out -> presenter.signOut()
            else -> return false
        }
        return true
    }

//    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
//        updateActionBar()
//        return super.onMenuOpened(featureId, menu)
//    }

    override fun onSignIn(user: User) {
        updateActionBar(true)
    }

    override fun onSignOut() {
        updateActionBar(false)
    }

    override fun startSignIn() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    override fun startSignOut() {
        Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show() // TODO: not implemented
    }

    private fun updateActionBar(loggedIn: Boolean) {
        if (loggedIn) {
            signIn.isVisible = false
            signOut.isVisible = true
        } else {
            signIn.isVisible = true
            signOut.isVisible = false
        }
    }

}
