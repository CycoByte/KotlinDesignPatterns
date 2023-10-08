package behavioural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


sealed class AuthorizationState {

}

object Unauthorized: AuthorizationState()
class Authorized(val username: String): AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val isAuthorized: Boolean
        get() = state is Authorized

    val username: String
        get() {
            return (state as? Authorized)?.username ?: "Anonymouse"
        }

    fun login(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }

    override fun toString(): String {
        return "User $username is logged in $isAuthorized"
    }
}

class StateTest {
    @Test
    fun testState() {
        val authPresenter = AuthorizationPresenter()
        authPresenter.login("user")
        println(authPresenter)
        Assertions.assertEquals(true, authPresenter.isAuthorized)
        Assertions.assertEquals("user", authPresenter.username)
        authPresenter.logoutUser()
        Assertions.assertEquals(false, authPresenter.isAuthorized)
        Assertions.assertEquals("Anonymouse", authPresenter.username)
    }
}