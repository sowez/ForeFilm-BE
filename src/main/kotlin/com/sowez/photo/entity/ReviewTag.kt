package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class ReviewTag (
    review: Review,
    tag: Tag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "review_tag_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "review_id")
    var review: Review = review
        protected set
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "tag_id")
    var tag: Tag = tag
        protected set
}