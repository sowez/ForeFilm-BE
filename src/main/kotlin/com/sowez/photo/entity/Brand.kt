package com.sowez.photo.entity

import jakarta.persistence.*

class Brand(
        image: Image,
        name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "brand_id")
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "brand_logo_image_id")
    var image: Image = image
        protected set

    @Column(nullable = false, name = "brand_name")
    var name: String = name
        protected set

}