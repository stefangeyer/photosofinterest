package at.renehollander.photosofinterest.image

import javax.inject.Inject

class ImagePresenter @Inject constructor(): ImageContract.Presenter {

    companion object {
        val MODE_CREATE = 0
        val MODE_DISPLAY = 1
    }

    private var view: ImageContract.View? = null

    override fun takeView(view: ImageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun init() {
        this.view?.enableViewMode()
        this.view?.updateTitle("Flughafen Innsbruck")
        this.view?.updateContent("https://www.innsbruck.info/emobilder/1000cx550c/12389/Flughafen---Christian-Schoepf.jpg")
    }

    override fun onImageClicked() {
        this.view?.toggleControls()
    }

    override fun onBackButtonClicked() {
        this.view?.returnResult()
    }

    override fun onConfirmButtonClicked() {
        this.view?.returnResult()
    }
}