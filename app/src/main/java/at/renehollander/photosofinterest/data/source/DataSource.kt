package at.renehollander.photosofinterest.data.source

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