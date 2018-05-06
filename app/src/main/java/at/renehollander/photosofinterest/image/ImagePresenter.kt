package at.renehollander.photosofinterest.image

class ImagePresenter: ImageContract.Presenter {

    private var view: ImageContract.View? = null

    override fun takeView(view: ImageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}