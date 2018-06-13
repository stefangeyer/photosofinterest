package at.renehollander.photosofinterest.challenges

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import at.renehollander.photosofinterest.R

class ChallengesFragmentPagerAdapter(
        private val fragmentManager: FragmentManager,
        private val context: Context,
        private val nearby: Fragment,
        private val ongoing: Fragment,
        private val all: Fragment
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> nearby
            1 -> ongoing
            2 -> all
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.challenges_category_nearby)
            1 -> context.getString(R.string.challenges_category_ongoing)
            2 -> context.getString(R.string.challenges_category_all)
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getCount(): Int = 3
}