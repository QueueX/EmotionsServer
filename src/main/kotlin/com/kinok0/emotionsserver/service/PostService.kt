package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.entity.PostEntity
import com.kinok0.emotionsserver.repository.PostRepository
import com.kinok0.emotionsserver.request.CreatePostRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val pythonRequestSender: PythonRequestSender
) {

    fun getAllUsers() : Any {
        try {
            val posts = postRepository.findAll().sortedByDescending { it.id }
            return ResponseEntity(posts, HttpStatus.OK)
        } catch (ex : Exception) {
            return ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

    fun getPostsByLabel(label: Int) : Any {
        try {
            val posts = postRepository.findPostEntitiesByLabel(label).sortedByDescending { it.id }
            return ResponseEntity(posts, HttpStatus.OK)
        } catch (ex : Exception) {
            return ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

    fun createPost(request : CreatePostRequest): Any {
        try {
            val currentTime = System.currentTimeMillis()
            val date = Date(currentTime)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate = sdf.format(date)

            val label = pythonRequestSender.sendRequest(request.text)

            if (label != -1) {

                val post = PostEntity().apply {
                    this.username = request.username
                    this.text = request.text
                    this.label = label
                    this.date = formattedDate
                }
                postRepository.save(post)

                val posts = postRepository.findAll().sortedByDescending { it.id }
                return ResponseEntity(posts, HttpStatus.OK)
            } else {
                return ResponseEntity("Failed to send request to Python server", HttpStatus.BAD_REQUEST)
            }
        } catch (ex : Exception) {
            return ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

}