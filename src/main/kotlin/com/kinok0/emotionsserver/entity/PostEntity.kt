package com.kinok0.emotionsserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "post")
class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(length = 50)
    var username: String? = null

    @Column(length = 10000)
    var text: String? = null

    @Column
    var label: Int? = null

    @Column(length = 20)
    var date: String? = null

}