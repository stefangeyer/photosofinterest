package at.renehollander.photosofinterest.image

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

interface ImageContract {

    interface View : BaseView {
        /**
         * Displays or hides the controls of the image display
         */
        fun toggleControls()

        /**
         * Updates the title text input
         * @param title new title
         */
        fun updateTitle(title: String)

        /**
         * Updates the image source
         * @param contentUri the new content uri
         */
        fun updateContent(contentUri: String)

        /**
         * Switches to parentView mode
         */
        fun enableViewMode()

        /**
         * Switches to create mode
         */
        fun enableCreateMode()

        /**
         * Title was provided by viewer
         * @return True if a title was provided
         */
        fun titleProvided(): Boolean

        /**
         * Tell user to provide a title
         */
        fun showTitleMissingAlert()

        /**
         * Returns the current result
         * @param canceled View canceled
         */
        fun returnResult(canceled: Boolean)
    }

    interface Presenter : BasePresenter<View> {

        /**
         * Initializes the parentView with the given mode, title and content
         * @param mode the mode to switch to
         * @param title the title to display
         * @param uriString the uri to get the content from
         */
        fun init(mode: Int, title: String, uriString: String)

        /**
         * Callback for clicks on the image
         */
        fun onImageClicked()

        /**
         * Callback for clicks Back button
         */
        fun onBackButtonClicked()

        /**
         * Callback for clicks on confirm button
         */
        fun onConfirmButtonClicked()
    }
}