package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Post

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface PostDataSource {

    /**
     * Callback for multiple elements
     */
    interface LoadRecordCallback<in R> {
        fun onRecordsLoaded(records: List<R>)
        fun onDataNotAvailable()
    }

    /**
     * Callback for single elements
     */
    interface GetRecordCallback<in R> {
        fun onRecordLoaded(record: R)
        fun onDataNotAvailable()
    }

    // Entity methods

    fun loadPosts(callback: LoadRecordCallback<Post>)
}