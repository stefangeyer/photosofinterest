package at.renehollander.photosofinterest.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.fragment.ChallengesFragment
import at.renehollander.photosofinterest.fragment.FeedFragment
import at.renehollander.photosofinterest.fragment.ProfileFragment
import at.renehollander.photosofinterest.fragment.ScoreboardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment: Fragment
        when (item.itemId) {
            R.id.navigation_feed -> {
                selectedFragment = FeedFragment.newInstance()
            }
            R.id.navigation_challenges -> {
                selectedFragment = ChallengesFragment.newInstance()
            }
            R.id.navigation_scoreboard -> {
                selectedFragment = ScoreboardFragment.newInstance()
            }
            R.id.navigation_profile -> {
                selectedFragment = ProfileFragment.newInstance()
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

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, FeedFragment.newInstance())
        transaction.commit()
    }
}
