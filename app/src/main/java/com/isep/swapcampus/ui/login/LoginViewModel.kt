package com.isep.swapcampus.ui.login
import androidx.lifecycle.ViewModel
import com.isep.shared.domain.entities.UserAccount
import com.isep.shared.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val currentUser: StateFlow<UserAccount?> = authRepository.currentUser

    fun login(email: String, password: String) {
        _error.value = when {
            email.isBlank() || password.isBlank() -> "Please fill in all fields"
            authRepository.authenticate(email, password) == null -> "Incorrect email or password"
            else -> null
        }
    }

    fun logout() = authRepository.logout()
}