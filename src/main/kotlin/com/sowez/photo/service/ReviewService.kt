package com.sowez.photo.service

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.*
import org.springframework.stereotype.Service
import java.util.*

@Service
interface ReviewService {
    fun createReview(createDto: ReviewCreateReqDto): Long
    fun getSingleReview(reviewId: Long): SingleReviewResDto
    fun getReviewImages(storeId: Long): ReviewImagesResDto
    fun getReviews(storeId: Long, limit: Int, offset: Int?): ReviewsResDto
    fun getReviewTags(storeId: Long): ReviewTagsResDto
}

@Service
class ReviewServiceTestImpl: ReviewService{
    override fun createReview(createDto: ReviewCreateReqDto): Long {
        println("ReviewServiceTestImpl.createReview")
        return 1L
    }

    override fun getSingleReview(reviewId: Long): SingleReviewResDto {
        println("ReviewServiceTestImpl.getSingleReview")
        return SingleReviewResDto(
            reviewNickname = "eumji",
            reviewProfile = "1",
            reviewCreatedDatetime = Date(System.currentTimeMillis()),
            reviewContents = "1",
            tags = listOf(
                TagResDto(1, "tag1", "emoji1"),
                TagResDto(2, "tag2", "emoji2")
            ),
            imageUrl = "jh"
        )
    }

    override fun getReviewImages(storeId: Long): ReviewImagesResDto {
        println("ReviewServiceTestImpl.getReviewImages")
        return ReviewImagesResDto(
            listOf(
                ReviewImageResDto(1, "image1"),
                ReviewImageResDto(2, "image2")
            )
        )

    }

    override fun getReviews(storeId: Long, limit: Int, offset: Int?): ReviewsResDto {
        println("ReviewServiceTestImpl.getReviews")
        return ReviewsResDto(
            listOf(
                ReviewResDto(
                    1,
                    "profileUrl",
                    Date(System.currentTimeMillis()),
                    "content",
                    listOf(
                        TagResDto(1,"1", "1")
                    ),
                    "url",
                    1
                )
            )
        )
    }

    override fun getReviewTags(storeId: Long): ReviewTagsResDto {
        println("ReviewServiceTestImpl.getReviewTags")
        return ReviewTagsResDto(
            totalCnt = 10,
            tags = listOf(
                ReviewTagResDto(
                    1,"1", "1", 1
                )
            )
        )
    }

}