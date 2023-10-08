package structural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

///// ----- 3rd party functionality
data class DisplayDataType(val index: Int, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

//// ----- Our code
data class DatabaseData(val itemId: Int, val amount: Int)

class DatabaseDataGenerator {
    fun getData(): List<DatabaseData> = listOf(
        DatabaseData(0, 20),
        DatabaseData(2, 30),
        DatabaseData(4, 40)
    )
}

interface DatabaseDataConverter {
    fun convert(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay): DatabaseDataConverter {
    override fun convert(data: List<DatabaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        data.forEachIndexed { index, databaseData ->
            val ddt = DisplayDataType(
                index = index,
                data = databaseData.amount.toString()
            )
            display.displayData(ddt)
            returnList.add(ddt)
        }
        return returnList
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val generatedData = DatabaseDataGenerator().getData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertData = adapter.convert(generatedData)
        Assertions.assertEquals(3, convertData.size)
        Assertions.assertEquals(0, convertData[0].index)
        Assertions.assertEquals("20", convertData[0].data)
    }
}