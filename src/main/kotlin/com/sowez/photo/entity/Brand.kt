package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Brand(
        logoImage: Image,
        name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "brand_id")
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "brand_logo_image_id")
    var logoImage: Image = logoImage
        protected set

    @Column(nullable = false, name = "brand_name")
    var name: String = name
        protected set

}