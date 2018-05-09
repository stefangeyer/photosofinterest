package at.renehollander.photosofinterest.feed

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import at.renehollander.photosofinterest.R

class FeedFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        val context: Context,
        val rising: Fragment,
        val recent: Fragment,
        val top: Fragment
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> rising
            1 -> recent
            2 -> top
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.feed_category_rising)
            1 -> context.getString(R.string.feed_category_recent)
            2 -> context.getString(R.string.feed_category_top)
            else -> throw IllegalStateException("not implemented")
        }
    }

    override fun getCount(): Int = 3
}