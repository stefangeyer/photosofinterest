package at.renehollander.photosofinterest.image

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

interface ImageContract {
    interface View: BaseView {
        fun toggleControls()

        fun updateTitle(title: String)

        fun updateContent(contentUri: String)

        fun enableViewMode()

        fun enableCreateMode()

        fun returnResult()
    }

    interface Presenter: BasePresenter<View> {
        fun init()

        fun onImageClicked()

        fun onBackButtonClicked()

        fun onConfirmButtonClicked()
    }
}