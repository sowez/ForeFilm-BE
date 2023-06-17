package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Layout(
        image: Image,
        isDefaultLayout: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "layout_id")
    val id: Long = 0

    @OneToOne
    @JoinColumn(name = "layout_image_id")
    var image: Image = image
        protected set

    @Column(name = "is_default_layout")
    var isDefaultLayout: Boolean = isDefaultLayout
        protected set
}