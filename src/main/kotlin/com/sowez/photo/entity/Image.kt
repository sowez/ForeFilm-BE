package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Image(
        uuid: String,
        originalName: String,
        name: String,
        extension: String,
        path: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "image_id")
    val id: Long = 0

    @Column(name = "image_uuid")
    var uuid: String = uuid
        protected set

    @Column(nullable = false, name = "original_name")
    var originalName: String = originalName
        protected set

    @Column(nullable = false,name = "image_name")
    var name: String = name
        protected set

    @Column(nullable = false)
    var extension: String = extension
        protected set

    @Column(nullable = false, name = "image_path")
    var path: String = path
        protected set

}