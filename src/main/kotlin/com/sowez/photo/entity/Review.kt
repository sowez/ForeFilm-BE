package com.sowez.photo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

class Review (
    store: Store,
    contents: String,
    nickname: String,
    password: String,
    isDeleted: Boolean,
    profile: Int? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "review_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "review_store_id")
    var store: Store = store
        protected set

    @Column(nullable = false, name = "review_created_datetime")
    val createdDatetime: LocalDateTime = LocalDateTime.now()
    @Column(nullable = false, name = "review_updated_datetime")
    var updatedDatetime: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(nullable = false, name = "review_contents")
    var contents: String = contents
        protected set
    
    @Column(nullable = false, name = "review_nickname")
    var nickname: String = nickname
        protected set

    @Column(nullable = false, name = "review_password")
    var password: String = password
        protected set

    @Column(nullable = false, name = "is_deleted_review")
    var isDeleted: Boolean = isDeleted
        protected set

    @Column(name = "review_profile")
    var profile: Int = profile
        protected set
}