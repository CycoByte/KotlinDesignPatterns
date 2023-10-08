package creational

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface Datasource

class DatabaseDatasource: Datasource
class NetworkDatasource: Datasource

abstract class DatasourceFactory {
    abstract fun makeDatasource(): Datasource

    companion object {
        inline fun <reified T: Datasource> createFactory(): DatasourceFactory = when(T::class) {
            DatabaseDatasource::class -> DatabaseFactory()
            NetworkDatasource::class -> NetworkFactory()
            else -> throw IllegalAccessException()
        }
    }

}

class NetworkFactory: DatasourceFactory() {
    override fun makeDatasource(): Datasource = NetworkDatasource()
}

class DatabaseFactory: DatasourceFactory() {
    override fun makeDatasource(): Datasource = DatabaseDatasource()
}

class AbstractFactoryTest {
    @Test
    fun testFactory() {
        val datasourceFactory = DatasourceFactory.createFactory<DatabaseDatasource>()
        val datasource = datasourceFactory.makeDatasource()

        println("Created datasource:$datasource")

        Assertions.assertEquals(DatabaseDatasource::class.java, datasource.javaClass)
    }
}