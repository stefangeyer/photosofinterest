package at.renehollander.photosofinterest.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.auth.AuthActivity
import at.renehollander.photosofinterest.challenges.ChallengesFragment
import at.renehollander.photosofinterest.feed.FeedFragment
import at.renehollander.photosofinterest.profile.ProfileFragment
import at.renehollander.photosofinterest.scoreboard.ScoreboardFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        // TODO remove
        this.presenter.performSomeAction()

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

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, this.feedFragment)
        transaction.commit()

        this.presenter.takeView(this)
    }

    override fun displaySomething() {
        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show()
    }
}
