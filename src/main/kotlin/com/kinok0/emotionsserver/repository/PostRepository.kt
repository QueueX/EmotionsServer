package com.kinok0.emotionsserver.repository

import com.kinok0.emotionsserver.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<PostEntity, Int> {
    fun findPostEntitiesByLabel(label: Int) : MutableList<PostEntity>
    fun deletePostEntityById(id: Int)
}