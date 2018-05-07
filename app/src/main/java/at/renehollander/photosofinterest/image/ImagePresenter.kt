package at.renehollander.photosofinterest.image

import javax.inject.Inject

class ImagePresenter @Inject constructor() : ImageContract.Presenter {

    companion object {
        const val MODE_CREATE = 0
        const val MODE_VIEW = 1
    }

    private var view: ImageContract.View? = null

    override fun takeView(view: ImageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun init(mode: Int, title: String, uriString: String) {
        when (mode) {
            MODE_CREATE -> this.view?.enableCreateMode()
            MODE_VIEW -> this.view?.enableViewMode()
            else -> this.view?.returnResult(true)
        }

        if (title.isNotBlank()) this.view?.updateTitle(title)
        if (uriString.isNotBlank()) this.view?.updateContent(uriString)
    }

    override fun onImageClicked() {
        this.view?.toggleControls()
    }

    override fun onBackButtonClicked() {
        this.view?.returnResult(true)
    }

    override fun onConfirmButtonClicked() {
        if (this.view?.titleProvided()!!) {
            this.view?.returnResult(false)
        } else {
            this.view?.showTitleMissingAlert()
        }
    }
}