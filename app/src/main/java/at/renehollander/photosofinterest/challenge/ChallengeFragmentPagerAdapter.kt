package at.renehollander.photosofinterest.challenge

import android.app.Application
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsFragment
import at.renehollander.photosofinterest.feed.FeedFragment
import javax.inject.Inject

class ChallengeFragmentPagerAdapter @Inject constructor(
        private val fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    @Inject
    lateinit var challengeDetailsFragment: ChallengeDetailsFragment
    @Inject
    lateinit var feedFragment: FeedFragment

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> challengeDetailsFragment
            1 -> feedFragment
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            0 -> application.getString(R.string.challenge_category_details)
//            1 -> application.getString(R.string.challenge_category_uploads)
//            else -> throw IllegalStateException("not implemented")
//        }
        return "Test"
    }

}