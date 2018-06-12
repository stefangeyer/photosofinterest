package at.renehollander.photosofinterest.challenge.domain.usecase

import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Post
import javax.inject.Inject

class LoadChallengePosts @Inject constructor(
) : UseCase<LoadChallengePosts.RequestValues, LoadChallengePosts.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {

    }

    class RequestValues(val challenge: Challenge) : UseCase.RequestValues

    class ResponseValue(val posts: List<Post>) : UseCase.ResponseValue
}