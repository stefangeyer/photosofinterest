package at.renehollander.photosofinterest.feed

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import at.renehollander.photosofinterest.R

class FeedFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        val context: Context,
        rising: Fragment,
        recent: Fragment,
        top: Fragment
) : FragmentPagerAdapter(fragmentManager) {

    private val screens = arrayOf(rising, recent, top)
    private val titles = arrayOf(R.string.feed_category_rising, R.string.feed_category_recent, R.string.feed_category_top)

    override fun getItem(position: Int): Fragment = this.screens[position]

    override fun getPageTitle(position: Int): CharSequence? =
            this.context.getString(this.titles[position])

    override fun getCount(): Int = screens.size
}