package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.entity.PostEntity
import com.kinok0.emotionsserver.repository.PostRepository
import com.kinok0.emotionsserver.request.CreatePostRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import kotlin.random.Random

@Service
class PostService(
    private val postRepository: PostRepository,
    private val random: Random
) {

    fun getAllUsers() : Any {
        return try {
            ResponseEntity(postRepository.findAll(), HttpStatus.OK)
        } catch (ex : Exception) {
            ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

    fun getPostsByLabel(label: Int) : Any {
        return try {
            ResponseEntity(postRepository.findPostEntitiesByLabel(label), HttpStatus.OK)
        } catch (ex : Exception) {
            ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

    fun createPost(request : CreatePostRequest): Any {
        try {
            val post = PostEntity().apply {
                this.username = request.username
                this.text = request.text
                this.label = random.nextInt(6)
            }
            postRepository.save(post)

            return ResponseEntity(post, HttpStatus.OK)
        } catch (ex : Exception) {
            return ResponseEntity(ex, HttpStatus.BAD_REQUEST)
        }
    }

}