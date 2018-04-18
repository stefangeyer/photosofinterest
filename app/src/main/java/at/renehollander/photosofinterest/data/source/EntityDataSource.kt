package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Entity

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface EntityDataSource {

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

    fun loadEntities(callback: LoadRecordCallback<Entity>)

    fun getEntity(name: String, callback: GetRecordCallback<Entity>)

    fun saveEntity(entity: Entity)

    fun deleteEntity(entity: Entity)

    fun deleteAllEntities()
}