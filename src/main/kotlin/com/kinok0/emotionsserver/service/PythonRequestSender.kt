package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.response.PythonResponse
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class PythonRequestSender {

    private val PYTHON_SERVER_URL = "http://python-server:5000/getLabel"

    fun sendRequest(text: String) : Int {
        val restTemplate = RestTemplate()

        val requestText = text.lowercase(Locale.getDefault())

        // Подсчет символов кириллицы и латиницы
        var cyrillicCount = 0
        var latinCount = 0

        for (char in requestText) {
            if (char in 'а'..'я' || char in 'А'..'Я') {
                cyrillicCount++
            } else if (char in 'a'..'z' || char in 'A'..'Z') {
                latinCount++
            }
        }

        // 0 - английская модель (латиница), 1 - русская модель (кириллица)
        val flag = if (cyrillicCount > latinCount) 1 else 0

        val url = "$PYTHON_SERVER_URL?flag=$flag&text=$requestText"

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
