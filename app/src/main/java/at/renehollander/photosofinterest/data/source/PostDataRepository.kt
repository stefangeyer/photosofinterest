package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.firestore.FirebaseFirestore
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
        val db: FirebaseFirestore
) : PostDataSource {
    override fun loadPosts(filter: PostDataSource.Filter, callback: LoadRecordCallback<Post>) {
        callback.onRecordsLoaded(mutableListOf())
    }
}