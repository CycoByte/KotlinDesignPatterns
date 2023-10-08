package behavioural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface HandlerChain {
    fun addHeader(input: String): String
}

class AuthenticationHeader(val token: String?, val next: HandlerChain? = null): HandlerChain {
    override fun addHeader(input: String): String = "$input\nAuthorization: $token".let { next?.addHeader(it) ?: it }
}


class ContentTypeHeader(val contentType: String?, val next: HandlerChain? = null): HandlerChain {
    override fun addHeader(input: String): String = "$input\nContent-Type: $contentType".let { next?.addHeader(it) ?: it }
}

class BodyPayloadHeader(val body: String?, val next: HandlerChain? = null): HandlerChain {
    override fun addHeader(input: String): String = "$input\nBody: $body".let { next?.addHeader(it) ?: it }
}

class ChainTest {
    @Test
    fun testChain() {
        val request = AuthenticationHeader("userToken", ContentTypeHeader("json", BodyPayloadHeader("<p>Some random data</p>")))
        val noChainRequest = ContentTypeHeader("json")
        println("------------")
        val requestHeader = request.addHeader("Headers with authentication")
        println(requestHeader)
        println("------------")

        Assertions.assertEquals("""
            Headers with authentication
            Authorization: userToken
            Content-Type: json
            Body: <p>Some random data</p>
        """.trimIndent(),
            requestHeader
        )

        Assertions.assertEquals("""
            test
            Content-Type: json
        """.trimIndent(),
            noChainRequest.addHeader("test")
        )
    }
}