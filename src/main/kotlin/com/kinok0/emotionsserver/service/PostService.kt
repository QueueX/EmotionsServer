package com.kinok0.emotionsserver.service

import com.kinok0.emotionsserver.entity.PostEntity
import com.kinok0.emotionsserver.repository.PostRepository
import com.kinok0.emotionsserver.request.CreatePostRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val pythonRequestSender: PythonRequestSender
) {
    private val logger: Logger = LoggerFactory.getLogger(PostService::class.java)

    fun getAllUsers() : Any {
        logger.info("Request to retrieve all posts")
        try {
            val posts = postRepository.findAll().sortedByDescending { it.id }
            logger.debug("Retrieved ${posts.size} posts\n")
            return ResponseEntity(posts, HttpStatus.OK)
        } catch (ex : Exception) {
            logger.error("Error getting all users: $ex\n")
            return ResponseEntity(mapOf("error" to ex.toString()), HttpStatus.BAD_REQUEST)
        }
    }

    fun getPostsByLabel(label: Int) : Any {
        logger.info("Request to retrieve posts with label: $label")
        try {
            val posts = postRepository.findPostEntitiesByLabel(label).sortedByDescending { it.id }
            logger.debug("Retrieved ${posts.size} posts with label: $label\n")
            return ResponseEntity(posts, HttpStatus.OK)
        } catch (ex : Exception) {
            logger.error("Error getting posts by label $label: $ex\n")
            return ResponseEntity(mapOf("error" to ex.toString()), HttpStatus.BAD_REQUEST)
        }
    }

    @Transactional
    fun createPost(request : CreatePostRequest): Any {
        logger.info("Request to creating post")
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
                logger.info("Post created: ${post.id}\n")

                val posts = postRepository.findAll().sortedByDescending { it.id }
                return ResponseEntity(posts, HttpStatus.OK)
            } else {
                logger.error("Failed to get label from Python server\n")
                return ResponseEntity(mapOf("error" to "Fail at Python server"), HttpStatus.BAD_REQUEST)
            }
        } catch (ex : Exception) {
            logger.error("Error creating post: $ex\n")
            return ResponseEntity(mapOf("error" to ex.toString()), HttpStatus.BAD_REQUEST)
        }
    }

    @Transactional
    fun deletePost(id: Int) : Any {
        logger.info("Request to delete post $id")
        try {
            postRepository.deleteById(id)
            logger.info("Post deleted: $id\n")
            return ResponseEntity(mapOf("message" to "Post has been deleted!"), HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("Error deleting post $id: $ex\n")
            return ResponseEntity(mapOf("error" to ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun deleteAllPosts() : Any {
        logger.info("Request to delete all posts")
        try {
            postRepository.deleteAll()
            logger.info("All posts have been deleted\n")
            return ResponseEntity(mapOf("message" to "All posts has been deleted!"), HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("Error deleting all posts: $ex\n")
            return ResponseEntity("error" to ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}