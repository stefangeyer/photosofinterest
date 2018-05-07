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

        fun titleProvided(): Boolean

        fun showTitleMissingAlert()

        fun returnResult(canceled: Boolean)
    }

    interface Presenter: BasePresenter<View> {
        fun init(mode: Int, title: String, uriString: String)

        fun onImageClicked()

        fun onBackButtonClicked()

        fun onConfirmButtonClicked()
    }
}