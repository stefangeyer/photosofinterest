package at.renehollander.photosofinterest.main.domain.usecase

import android.util.Log
import at.renehollander.photosofinterest.UseCase
import javax.inject.Inject

class ExampleUseCase @Inject constructor() : UseCase<ExampleUseCase.RequestValues, ExampleUseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        Log.d(TAG, requestValues!!.message)

        useCaseCallback?.onSuccess(ResponseValue())
    }

    class RequestValues(val message: String) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue

    companion object {
        private val TAG = "ExampleUseCase"
    }
}