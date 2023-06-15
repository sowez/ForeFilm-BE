package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class ReviewImage (
    review: Review,
    image: Image
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "review_image_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "review_id")
    var review: Review = review
        protected set
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "image_id")
    var image: Image = image
        protected set
}