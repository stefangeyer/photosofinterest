package at.renehollander.photosofinterest.auth.domain.usecase

import at.renehollander.photosofinterest.UseCase
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOut @Inject constructor(
        val auth: FirebaseAuth
) : UseCase<UseCase.RequestValues, UseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        auth.signOut()
        useCaseCallback?.onSuccess(object : ResponseValue {})
    }
}