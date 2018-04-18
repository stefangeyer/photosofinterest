package at.renehollander.photosofinterest.data.source.remote

import at.renehollander.photosofinterest.data.Entity
import at.renehollander.photosofinterest.data.source.EntityDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class RemoteEntityDataSource @Inject constructor(): EntityDataSource {
    override fun loadEntities(callback: EntityDataSource.LoadRecordCallback<Entity>) {
        // TODO("not implemented")
    }

    override fun getEntity(name: String, callback: EntityDataSource.GetRecordCallback<Entity>) {
        // TODO("not implemented")
    }

    override fun saveEntity(entity: Entity) {
        // TODO("not implemented")
    }

    override fun deleteEntity(entity: Entity) {
        // TODO("not implemented")
    }

    override fun deleteAllEntities() {
        // TODO("not implemented")
    }
}