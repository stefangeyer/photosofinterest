package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the feed view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface FeedContract {
    interface View : BaseView
    interface Presenter : BasePresenter<View>
}