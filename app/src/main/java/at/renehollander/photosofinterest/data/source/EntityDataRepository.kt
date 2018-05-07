package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Entity
import at.renehollander.photosofinterest.data.source.remote.RemoteEntityDataSource
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
class EntityDataRepository @Inject constructor(
        private val remoteDataSource: RemoteEntityDataSource
) : EntityDataSource {
    override fun loadEntities(callback: EntityDataSource.LoadRecordCallback<Entity>) {
        return this.remoteDataSource.loadEntities(callback)
    }

    override fun getEntity(name: String, callback: EntityDataSource.GetRecordCallback<Entity>) {
        return this.remoteDataSource.getEntity(name, callback);
    }

    override fun saveEntity(entity: Entity) {
        this.remoteDataSource.saveEntity(entity)
    }

    override fun deleteEntity(entity: Entity) {
        this.remoteDataSource.deleteEntity(entity)
    }

    override fun deleteAllEntities() {
        this.remoteDataSource.deleteAllEntities()
    }
}