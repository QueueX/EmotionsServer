package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.entity.PostEntity
import com.kinok0.emotionsserver.repository.PostRepository
import com.kinok0.emotionsserver.request.CreatePostRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Service
class PostService(
    private val postRepository: PostRepository,
    private val random: Random
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

            val post = PostEntity().apply {
                this.username = request.username
                this.text = request.text
                this.label = random.nextInt(6)
                this.date = formattedDate
            }
            postRepository.save(post)

            return ResponseEntity(post, HttpStatus.OK)
        } catch (ex : Exception) {
            return ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

}