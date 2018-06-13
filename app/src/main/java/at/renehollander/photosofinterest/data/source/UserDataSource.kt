package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.User

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface UserDataSource {
    fun hasUser(email: String, callback: GetRecordCallback<Boolean>)
    fun addUser(user: User, callback: GetRecordCallback<String>)
    fun loadUser(id: String, callback: GetRecordCallback<User>)
}