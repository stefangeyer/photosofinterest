package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView

/**
 * Contract for the main view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface MainContract {

    interface View : BaseView {
        fun displaySomething()
    }

    interface Presenter : BasePresenter<View> {
        fun performSomeAction()
    }
}