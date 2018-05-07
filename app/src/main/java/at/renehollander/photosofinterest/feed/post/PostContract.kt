package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

interface PostContract {
    interface View : BaseView {
        fun init()
    }

    interface Presenter : BasePresenter<View> {

    }
}