package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Post

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface PostDataSource {
    fun loadPosts(filter: Filter, callback: LoadRecordCallback<Post>)

    enum class Filter {
        RISING, RECENT, TOP
    }
}