package at.renehollander.photosofinterest

/**
 * Contains UI business logic operations. Relies on the parentView calling takeView on init.
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface BasePresenter<in V : BaseView> {

    /**
     * Binds presenter with a parentView when resumed. The ViewHolderPresenter will perform initialization here.
     *
     * @param view the parentView associated with this presenter
     */
    fun takeView(view: V)

    /**
     * Drops the reference to the parentView when destroyed
     */
    fun dropView()

}

/**
 * Contains operations that represent some visual operation in the parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface BaseView