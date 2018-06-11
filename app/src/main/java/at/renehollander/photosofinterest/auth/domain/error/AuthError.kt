package at.renehollander.photosofinterest.auth.domain.error

enum class AuthError {
    EMAIL_ALREADY_IN_USE, INVALID_EMAIL, WEAK_PASSWORD, WRONG_PASSWORD, USER_DISABLED,
    USER_NOT_FOUND, WRONG_PROVIDER, OTHER, GOOGLE_NO_SUCCESS
}