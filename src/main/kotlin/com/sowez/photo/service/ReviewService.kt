package com.sowez.photo.service

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.entity.*
import com.sowez.photo.error.ReviewNotFoundException
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.error.TagNotFoundException
import com.sowez.photo.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
interface ReviewService {
    fun createReview(createDto: ReviewCreateReqDto): Long
    fun getSingleReview(reviewId: Long): SingleReviewResDto
    fun getReviewImages(storeId: Long): ReviewImagesResDto
    fun getReviews(storeId: Long, limit: Int, offset: Int?): ReviewsResDto
    fun getReviewTags(storeId: Long): ReviewTagsResDto
}

@Service
class ReviewServiceTestImpl(
    val storeRepository: StoreRepository,
    val reviewRepository: ReviewRepository,
    val reviewTagRepository: ReviewTagRepository,
    val reviewImageRepository: ReviewImageRepository,
    val tagRepository: TagRepository,
    val imageRepository: ImageRepository
): ReviewService{
    override fun createReview(createDto: ReviewCreateReqDto): Long {
        val store = storeRepository.findById(createDto.storeId)
            .orElseThrow { StoreNotFoundException(createDto.storeId) }

        val review = reviewRepository.save(
            Review(
                store = store,
                nickname = createDto.reviewNickname,
                password = createDto.reviewPassword,
                contents = createDto.reviewContents,
                isDeleted = false,
            )
        )

        if (createDto.reviewTagIds != null) { // tag id로 tag 객체 찾기//
            for (i in 0 until createDto.reviewTagIds.size) {
                val tag = tagRepository.findById(createDto.reviewTagIds[i])
                    .orElseThrow { TagNotFoundException(createDto.reviewTagIds[i]) }
                reviewTagRepository.save(
                    ReviewTag(
                        review = review,
                        tag = tag
                    )
                )
            }
        }

        val reviewId = review.id
        if(createDto.reviewImages != null) {
            for (i in 0 until createDto.reviewImages.size) {
                val image = imageRepository.save(
                    Image(
                        uuid = "uuid_$reviewId"+"_$i",
                        originalName = createDto.reviewImages[i],
                        name = "name_$reviewId"+"_$i",
                        extension = "jpeg",
                        path = "path_$reviewId"+"_$i"
                    )
                )

                reviewImageRepository.save(
                    ReviewImage(
                        review = review,
                        image = image
                    )
                )
            }
        }

        return review.id
    }

    override fun getSingleReview(reviewId: Long): SingleReviewResDto {
        println("ReviewServiceTestImpl.getSingleReview")

        val review = reviewRepository.findById(reviewId)
            .orElseThrow{ ReviewNotFoundException(reviewId) }
        val tagIds = reviewTagRepository.findTagIdsWithReviewId(reviewId)
        val imageIds = reviewImageRepository.findImageIdsWithReviewId(reviewId)
        val tagResDtos = tagRepository.findAllById(tagIds)
            .map{ tag -> TagResDto(tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName)}
        val imageResDtos = imageRepository.findAllById(imageIds)
            .map{ image -> ReviewImageResDto(imageId = image.id, imageUrl = image.path)}

        return SingleReviewResDto(
            reviewNickname = review.nickname,
            reviewContents = review.contents,
            reviewCreatedDatetime = review.createdDatetime,
            reviewTags = tagResDtos,
            reviewImages = imageResDtos,
        )
    }

    override fun getReviewImages(storeId: Long): ReviewImagesResDto {
        println("ReviewServiceTestImpl.getReviewImages")
        return ReviewImagesResDto(
            listOf(
                ReviewImageResDto(1, "https://www.forefilm.com/images/1"),
                ReviewImageResDto(2, "https://www.forefilm.com/images/2")
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
                    "content",
                    LocalDateTime.of(2023,8,12,13,35, 1),
                    listOf(
                        TagResDto(1,"1", "1")
                    ),
                    "url",
                    1
                )
            ),
            lastReviewId = 10
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