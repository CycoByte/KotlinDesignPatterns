package structural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class ComplexSystemStore(private val filePath: String) {
    private val cache: HashMap<String, String> = hashMapOf()

    init {
        println("Initializing $this - $filePath")
        //Read properties to file in cache...
    }

    fun store(key: String, value: String) {
        cache[key] = value
    }

    fun read(key: String): String = cache[key] ?: ""

    fun commit() {
        println("Storing cache data to file: $filePath")
    }
}

data class User(val login: String)

//Facade
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/users.prefs")
    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

class FacadeTest {
    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("john wick")
        userRepo.save(user)
        val retrieveUser = userRepo.findFirst()

        Assertions.assertEquals("john wick", retrieveUser.login)
    }
}