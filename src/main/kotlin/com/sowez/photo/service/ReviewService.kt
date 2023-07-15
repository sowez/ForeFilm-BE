package com.sowez.photo.service

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.*
import org.springframework.stereotype.Service

@Service
interface ReviewService {
    fun createReview(createDto: ReviewCreateReqDto): Long
    fun getSingleReview(reviewId: Long): SingleReviewResDto
    fun getReviewImages(storeId: Long): ReviewImagesResDto
    fun getReviews(storeId: Long, limit: Int, offset: Int): ReviewsResDto
    fun getReviewTags(storeId: Long): ReviewTagsResDto
}