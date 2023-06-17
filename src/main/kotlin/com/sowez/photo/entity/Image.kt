package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Image(
        imageUuid: String,
        originalName: String,
        imageName: String,
        extension: String,
        imagePath: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "image_id")
    val id: Long = 0

    @Column(name = "image_uuid")
    var imageUuid: String = imageUuid
        protected set

    @Column(nullable = false, name = "original_name")
    var originalName: String = originalName
        protected set

    @Column(name = "image_name")
    var imageName: String = imageName
        protected set

    @Column(nullable = false, name = "extension")
    var extension: String = extension
        protected set

    @Column(nullable = false, name = "image_path")
    var imagePath: String = imagePath
        protected set

}