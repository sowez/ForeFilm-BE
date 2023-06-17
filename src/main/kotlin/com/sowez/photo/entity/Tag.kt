package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class Tag (
    contents: String,
    emojiName: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "tag_id")
    val id: Long = 0

    @Column(nullable = false, name = "tag_contents")
    var contents: String = contents
        protected set    
    @Column(nullable = false, name = "tag_emoji_name")
    var emojiName: String = emojiName
        protected set
}