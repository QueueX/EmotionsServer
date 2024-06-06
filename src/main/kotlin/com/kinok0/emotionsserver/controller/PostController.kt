package com.kinok0.emotionsserver.controller

import com.kinok0.emotionsserver.request.CreatePostRequest
import com.kinok0.emotionsserver.service.PostService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class PostController(private val postService: PostService) {

    @GetMapping("/")
    fun getAllPosts() = postService.getAllUsers()

    @GetMapping("/{label}")
    fun getPostsByLabel(@PathVariable label: Int) = postService.getPostsByLabel(label)

    @PostMapping("/")
    fun createPost(@RequestBody request : CreatePostRequest) = postService.createPost(request)

    @DeleteMapping("/")
    fun deleteAllPosts() = postService.deleteAllPosts()

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Int) = postService.deletePost(id)

}