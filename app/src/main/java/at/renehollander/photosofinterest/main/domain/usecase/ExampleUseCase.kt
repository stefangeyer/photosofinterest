package at.renehollander.photosofinterest.main.domain.usecase

import android.util.Log
import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.source.EntityDataSource
import javax.inject.Inject

class ExampleUseCase @Inject constructor(
        private val dataSource: EntityDataSource
) : UseCase<ExampleUseCase.RequestValues, ExampleUseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        Log.d(Companion.TAG, requestValues!!.message)

        this.dataSource.deleteAllEntities()

        useCaseCallback?.onSuccess(ResponseValue())
    }

    class RequestValues(val message: String) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue

    companion object {
        private val TAG = "ExampleUseCase"
    }
}