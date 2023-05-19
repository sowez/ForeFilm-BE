package com.sowez.photo.repository

import com.sowez.photo.entity.ReviewImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewImageRepository: JpaRepository<ReviewImage, Long> {
}