package at.renehollander.photosofinterest.image

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

interface ImageContract {
    interface View: BaseView {
        fun toggleControls()
    }

    interface Presenter: BasePresenter<View> {

    }
}