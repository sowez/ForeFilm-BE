package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class BoothBackgroundColor(
    name: String,
    code: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "booth_background_color_id")
    val id: Long = 0

    @Column(nullable = false, name = "booth_background_color_name")
    var name: String = name
        protected set
    @Column(nullable = false, name = "booth_background_color_code")
    var code: String = code
        protected set

}