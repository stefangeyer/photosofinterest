package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.remote.RemotePostDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import javax.inject.Inject

/**
 * Implementation of DataSource using a Repository Pattern.
 * This can be used to have both, a local and a remote Repository
 * and merge their operations in this class
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
@ApplicationScoped
class PostDataRepository @Inject constructor(
        private val remoteDataSource: RemotePostDataSource
) : PostDataSource {
    override fun loadPosts(filter: Filter, callback: LoadRecordCallback<Post>) {
        return this.remoteDataSource.loadPosts(filter, callback)
    }
}