package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.User
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
class UserDataRepository @Inject constructor(
        val db: FirebaseFirestore
) : UserDataSource {
    override fun loadUser(id: String, callback: GetRecordCallback<User>) {
        db.collection("users").document(id).get().addOnSuccessListener {
            if (it.exists()) {
                callback.onRecordLoaded(it.toObject(User::class.java)!!)
            } else {
                callback.onDataNotAvailable()
            }
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

    override fun hasUser(id: String, callback: GetRecordCallback<Boolean>) {
        db.collection("users").document(id).get().addOnSuccessListener {
            callback.onRecordLoaded(it.exists())
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

    override fun addUser(user: User, callback: GetRecordCallback<User>) {
        db.collection("users").document(user.id).set(user).addOnSuccessListener {
            callback.onRecordLoaded(user)
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

}