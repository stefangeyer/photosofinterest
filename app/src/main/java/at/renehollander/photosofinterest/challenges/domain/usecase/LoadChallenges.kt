package at.renehollander.photosofinterest.challenges.domain.usecase

import android.util.Log
import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.challenges.domain.model.RequestFilter
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.source.ChallengeDataSource
import at.renehollander.photosofinterest.data.source.ChallengeDataSource.Filter
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import javax.inject.Inject

class LoadChallenges @Inject constructor(
        private val dataSource: ChallengeDataSource
) : UseCase<LoadChallenges.RequestValues, LoadChallenges.ResponseValue>() {

    companion object {
        const val TAG = "LoadChallenges"
    }

    override fun executeUseCase(requestValues: RequestValues?) {
        Log.d(TAG, "Fetching challenges from remote")

        var remoteFilter: Filter = Filter.NEARBY

        when (requestValues?.filter) {
            RequestFilter.NEARBY -> remoteFilter = Filter.NEARBY
            RequestFilter.ONGOING -> remoteFilter = Filter.ONGOING
            RequestFilter.ALL -> remoteFilter = Filter.ALL
        }

        this.dataSource.loadChallenges(remoteFilter, object : LoadRecordCallback<Challenge> {
            override fun onRecordsLoaded(records: List<Challenge>) {
                this@LoadChallenges.useCaseCallback?.onSuccess(ResponseValue(records))
                Log.d(TAG, "Fetching challenges was successful")
            }

            override fun onDataNotAvailable() {
                this@LoadChallenges.useCaseCallback?.onError()
                Log.d(TAG, "Fetching challenges did not produce any data")
            }
        })
    }

    class RequestValues(val filter: RequestFilter) : UseCase.RequestValues

    class ResponseValue(val challenges: List<Challenge>) : UseCase.ResponseValue
}