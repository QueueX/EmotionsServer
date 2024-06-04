package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.response.PythonResponse
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class PythonRequestSender {

    private val PYTHON_SERVER_URL = "http://localhost:5000/getLabel"

    fun sendRequest(text: String) : Int {
        val restTemplate = RestTemplate()

        val requestText = text.lowercase(Locale.getDefault()).replace(" ", "%20")

        val url = "$PYTHON_SERVER_URL?text=$requestText"

        try {
            val response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                PythonResponse::class.java
            )

            return response.body!!.label
        } catch (e: HttpClientErrorException) {
            return -1
        }
    }

}