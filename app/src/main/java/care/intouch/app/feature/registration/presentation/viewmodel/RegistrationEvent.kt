package care.intouch.app.feature.registration.presentation.viewmodel

sealed class RegistrationEvent {
    data class OnGetUserInfo(
        val userId: String?,
        val token: String?
    ): RegistrationEvent()

    data class OnSetPassword(
        val password: String,
        val confirmPassword: String,
    ): RegistrationEvent()
}