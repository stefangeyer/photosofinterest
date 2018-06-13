package at.renehollander.photosofinterest.challenge

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsFragment
import at.renehollander.photosofinterest.feed.post.PostFragment

class ChallengeFragmentPagerAdapter constructor(
        fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    lateinit var context: Context
    lateinit var challengeDetailsFragment: ChallengeDetailsFragment
    lateinit var postFragment: PostFragment

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> challengeDetailsFragment
            1 -> this.postFragment
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.challenge_category_details)
            1 -> context.getString(R.string.challenge_category_uploads)
            else -> throw IllegalStateException("not implemented")
        }
    }

}