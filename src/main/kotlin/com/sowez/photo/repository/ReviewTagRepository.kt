package com.sowez.photo.repository

import com.sowez.photo.entity.ReviewTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewTagRepository: JpaRepository<ReviewTag, Long> {
}