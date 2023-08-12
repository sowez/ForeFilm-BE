package com.sowez.photo.service

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.ReviewImagesResDto
import com.sowez.photo.dto.res.ReviewTagsResDto
import com.sowez.photo.dto.res.ReviewsResDto
import com.sowez.photo.dto.res.SingleReviewResDto
import org.springframework.stereotype.Service

@Service
interface ReviewService {
    fun createReview(createDto: ReviewCreateReqDto): Long
    fun getSingleReview(reviewId: Long): SingleReviewResDto
    fun getReviewImages(storeId: Long): ReviewImagesResDto
    fun getReviews(storeId: Long, limit: Int, offset: Int): ReviewsResDto
    fun getReviewTags(storeId: Long): ReviewTagsResDto
}


@Service
class ReviewServiceTestImpl: ReviewService {
    override fun createReview(createDto: ReviewCreateReqDto): Long {
        TODO("Not yet implemented")
    }

    override fun getSingleReview(reviewId: Long): SingleReviewResDto {
        TODO("Not yet implemented")
    }

    override fun getReviewImages(storeId: Long): ReviewImagesResDto {
        TODO("Not yet implemented")
    }

    override fun getReviews(storeId: Long, limit: Int, offset: Int): ReviewsResDto {
        TODO("Not yet implemented")
    }

    override fun getReviewTags(storeId: Long): ReviewTagsResDto {
        TODO("Not yet implemented")
    }

}