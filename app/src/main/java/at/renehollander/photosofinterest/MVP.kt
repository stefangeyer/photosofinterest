package at.renehollander.photosofinterest

/**
 * Contains UI business logic operations. Relies on the view calling takeView on init.
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface BasePresenter<in V : BaseView> {

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    fun takeView(view: V)

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

}

/**
 * Contains operations that represent some visual operation in the view
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface BaseView