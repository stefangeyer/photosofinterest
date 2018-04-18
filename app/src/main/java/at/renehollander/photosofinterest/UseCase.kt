package at.renehollander.photosofinterest

import android.os.Handler
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    var requestValues: Q? = null
    var useCaseCallback: UseCaseCallback<P>? = null

    internal fun run() {
        executeUseCase(requestValues)
    }

    protected abstract fun executeUseCase(requestValues: Q?)

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    interface UseCaseCallback<in R> {
        fun onSuccess(response: R)
        fun onError()
    }
}

/**
 * Runs [UseCase]s using a [UseCaseScheduler].
 */
class UseCaseHandler(private val scheduler: UseCaseScheduler) {
    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
            useCase: UseCase<T, R>, values: T,
            onSuccess: (response: R) -> Unit, onError: () -> Unit) {
        execute(useCase, values, object : UseCase.UseCaseCallback<R> {
            override fun onSuccess(response: R) {
                onSuccess(response)
            }

            override fun onError() {
                onError()
            }
        })
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)

        scheduler.execute(Runnable { useCase.run() })
    }

    private fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        scheduler.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValue> notifyError(useCaseCallback: UseCase.UseCaseCallback<V>) {
        scheduler.onError(useCaseCallback)
    }

    private class UiCallbackWrapper<in V : UseCase.ResponseValue>(
            private val callback: UseCase.UseCaseCallback<V>,
            private val useCaseHandler: UseCaseHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            useCaseHandler.notifyResponse(response, callback)
        }

        override fun onError() {
            useCaseHandler.notifyError(callback)
        }
    }
}

/**
 * Interface for schedulers, see [UseCaseThreadPoolScheduler].
 */
interface UseCaseScheduler {

    fun execute(runnable: Runnable)

    fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>)

    fun <V : UseCase.ResponseValue> onError(useCaseCallback: UseCase.UseCaseCallback<V>)
}

/**
 * Executes asynchronous tasks using a [ThreadPoolExecutor].
 *
 *
 * See also [Executors] for a list of factory methods to create common
 * [java.util.concurrent.ExecutorService]s for different scenarios.
 */
class UseCaseThreadPoolScheduler : UseCaseScheduler {

    private val handler = Handler()

    private val threadPoolExecutor: ThreadPoolExecutor =
            ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE))

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        handler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValue> onError(useCaseCallback: UseCase.UseCaseCallback<V>) {
        handler.post { useCaseCallback.onError() }
    }

    companion object {
        val POOL_SIZE = 2
        val MAX_POOL_SIZE = 4
        val TIMEOUT = 30L
    }
}
