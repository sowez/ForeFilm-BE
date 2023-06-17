package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Image(
        image_uuid: String,
        original_name: String,
        image_name: String,
        extension: String,
        image_path: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "image_id")
    val id: Long = 0

    @Column(name = "image_uuid")
    var image_uuid: String = image_uuid
        protected set

    @Column(nullable = false, name = "original_name")
    var original_name: String = original_name
        protected set

    @Column(name = "image_name")
    var image_name: String = image_name
        protected set

    @Column(nullable = false, name = "extension")
    var extension: String = extension
        protected set

    @Column(nullable = false, name = "image_path")
    var image_path: String = image_path
        protected set

}