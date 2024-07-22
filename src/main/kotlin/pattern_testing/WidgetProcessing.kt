package pattern_testing

import kotlinx.coroutines.*

fun main() = runBlocking {
    val widgetManager = WidgetManagerWP(
        UserWP(
            widgets = listOf(UserWP.Widget.MESSAGES, UserWP.Widget.ADS, UserWP.Widget.QUICK_TRANSFER),
            name = "Kokos",
            id = "1"
        )
    )
    widgetManager.loadUserWidgets(reload = false)

    widgetManager.widgets.forEach {
        when(it.id) {
            UserWP.Widget.ADS -> {
                val t = it.uiData as UIStateWP.Success<List<String>>
                println("Loaded widget ADS data: ${t.data.joinToString(" - ") { it }}")
            }
            UserWP.Widget.MESSAGES -> {
                val t = it.uiData as UIStateWP.Success<List<String>>
                println("Loaded widget MESSAGES data: ${t.data.joinToString(" - ") { it }}")
            }
            UserWP.Widget.QUICK_TRANSFER -> {
                val t = it.uiData as UIStateWP.Success<List<String>>
                println("Loaded widget QUICK_TRANSFER data: ${t.data.joinToString(" - ") { it }}")
            }
        }
    }
}



interface IWidgetManagerWP {
    val widgets: List<IWidgetWP>
    suspend fun loadUserWidgets(reload: Boolean)
}

class WidgetManagerWP(user: UserWP): IWidgetManagerWP {

    override val widgets: List<IWidgetWP> = user.widgets.map {
        println("Loading user widget: ${it}")
        when(it) {
            UserWP.Widget.ADS -> AdsWidget(AdsRepositoryWP())
            UserWP.Widget.MESSAGES -> UserMessagesWidget(UserRepositoryWP())
            UserWP.Widget.QUICK_TRANSFER -> QuickTransferWidget(UserRepositoryWP())
        }
    }

    override suspend fun loadUserWidgets(reload: Boolean) {
        coroutineScope {
            println("loadUserWidgets(...) - START")
            if (reload) {
                widgets.forEach { it.clear() }
            }
            val jobs: MutableList<Job> = mutableListOf()
            widgets.forEach {
                jobs.add(async { it.load() })
            }
            jobs.joinAll()
            println("loadUserWidgets(...) - END")
        }
    }
}


sealed interface IWidgetWP {
    var uiData: UIStateWP<*>
    var isActive: Boolean
    val id: UserWP.Widget
    suspend fun load()
    suspend fun clear()
}


//Quick Transfer Widget
class QuickTransferWidget(private val repo: UserRepositoryWP): IWidgetWP {
    override var uiData: UIStateWP<*> = UIStateWP.Idle
    override var isActive: Boolean = false
    override val id: UserWP.Widget = UserWP.Widget.QUICK_TRANSFER

    override suspend fun load() {
        println("$this load(...) - START")
        uiData = UIStateWP.Loading
        delay(1200L)
        uiData = UIStateWP.Success(repo.recentTransactions())
        println("$this load(...) - END")
    }

    override suspend fun clear() {
        uiData = UIStateWP.Idle
    }
}

//Messages Widget Implementation
class UserMessagesWidget(private val repo: UserRepositoryWP): IWidgetWP {

    override var uiData: UIStateWP<*> = UIStateWP.Idle
    override var isActive: Boolean = false
    override val id: UserWP.Widget = UserWP.Widget.MESSAGES

    override suspend fun load() {
        println("$this load(...) - START")
        uiData = UIStateWP.Loading
        delay(1200L)
        uiData = UIStateWP.Success(repo.fetchUserMessages())
        println("$this load(...) - END")
    }

    override suspend fun clear() {
        uiData = UIStateWP.Idle
    }
}

//Ads Widget Implementation
class AdsWidget(private val repo: AdsRepositoryWP): IWidgetWP {

    override var uiData: UIStateWP<*> = UIStateWP.Idle
    override var isActive: Boolean = false
    override val id: UserWP.Widget = UserWP.Widget.ADS

    override suspend fun load() {
        println("$this load(...) - START")
        uiData = UIStateWP.Loading
        delay(1200L)
        uiData = UIStateWP.Success(repo.fetchAds())
        println("$this load(...) - END")
    }

    override suspend fun clear() {
        uiData = UIStateWP.Idle
    }
}

//Demo Ads Repository
class AdsRepositoryWP {
    fun fetchAds(): List<String> = listOf(
        "Add 1",
        "Add 2",
        "Add 3",
        "Add 4",
        "Add 5"
    )
}

//Demo user repository
class UserRepositoryWP {
    fun fetchUserMessages(): List<String> {
        return listOf(
            "Message 1",
            "Message 2",
            "Message 3",
            "Message 4",
            "Message 5"
        )
    }

    fun recentTransactions() = listOf(
        "Transaction 1",
        "Transaction 2",
        "Transaction 3",
        "Transaction 4",
        "Transaction 5"
    )
}

//Widget Data state
sealed interface UIStateWP<T> {
    data object Idle: UIStateWP<Unit>
    data object Loading: UIStateWP<Unit>
    data class Success<T>(val data: T): UIStateWP<T>
}

data class UserWP(
    val name: String,
    val id: String,
    val widgets: List<Widget>
) {
    enum class Widget {
        MESSAGES,
        ADS,
        QUICK_TRANSFER
    }
}