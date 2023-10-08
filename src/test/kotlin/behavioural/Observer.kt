package behavioural

import org.junit.jupiter.api.Test
import java.io.File


interface EventListener {
    fun update(eventType: String?, file: File?)
}

class EventManager(vararg operations: String) {
    private var listeners = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for (operation in operations) {
            listeners[operation] = ArrayList()
        }
    }

    fun subscribe(eventType: String?, listener: EventListener) {
        val users = listeners.get(eventType)
        users?.add(listener)
    }

    fun unsubscribe(eventType: String?, listener: EventListener) {
        val users = listeners.get(eventType)
        users?.remove(listener)
    }

    fun notify(eventType: String?, file: File?) {
        val users = listeners.get(eventType)
        users?.let {
            for (listener in it)
                listener.update(eventType, file)
        }
    }
}

class Editor {
    var events: EventManager = EventManager("open", "save", "close")

    private var file: File? = null

    fun openFile(filePath: String) {
        file = File(filePath)
        events.notify("open", file)
    }

    fun saveFile() {
        if(file != null) {
            events.notify("save", file)
        }
    }
}

class EmailNotificationListener(private val email: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Email to $email: Someone has performed: $eventType on file: $file")
    }
}

class FileLogListener(private val filename: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Logging to $filename: Someone has performed: $eventType on file: $file")
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()
        val fileLog = FileLogListener("path/to/fileLog.log")
        val fileEmail = EmailNotificationListener("someone@test.com")
        editor.events.subscribe("open", fileLog)
        editor.events.subscribe("open", fileEmail)
        editor.events.subscribe("save", fileEmail)

        editor.openFile("test.txt")
        editor.saveFile()
    }
}