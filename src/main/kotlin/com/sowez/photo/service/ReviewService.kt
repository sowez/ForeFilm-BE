package com.sowez.photo.service

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.entity.*
import com.sowez.photo.error.ReviewNotFoundException
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.error.TagNotFoundException
import com.sowez.photo.repository.*
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
interface ReviewService {
    fun createReview(createDto: ReviewCreateReqDto): Long
    fun getSingleReview(reviewId: Long): SingleReviewResDto
    fun getReviews(storeId: Long, limit: Int, offset: Int?): ReviewsResDto
    fun getReviewImages(storeId: Long, limit: Int): ReviewImagesResDto
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
            for (i in createDto.reviewTagIds.indices) {
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
            for (i in createDto.reviewImages.indices) {
                val image = imageRepository.save(
                    Image(
                        uuid = UUID.randomUUID().toString(),
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
        val review = reviewRepository.findById(reviewId)
            .orElseThrow{ ReviewNotFoundException(reviewId) }
        val tagIds = reviewTagRepository.findTagIdsWithReviewId(reviewId)
        val imageIds = reviewImageRepository.findImageIdsWithReviewId(reviewId)
        val tagResDtos = tagRepository.findAllById(tagIds)
            .map{ tag -> TagResDto(tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName)}
        val imageResDtos = imageRepository.findAllById(imageIds)
            .map{ image -> ReviewImageResDto(imageId = image.id, imageUrl = getImageUrl(image))}

        return SingleReviewResDto(
            reviewNickname = review.nickname,
            reviewContents = review.contents,
            reviewCreatedDatetime = review.createdDatetime,
            reviewTags = tagResDtos,
            reviewImages = imageResDtos,
        )
    }

    override fun getReviews(storeId: Long, limit: Int, offset: Int?): ReviewsResDto {
        val reviewIds = if (offset != null){
            reviewRepository.findNextReviewIds(storeId, offset, PageRequest.of(0, limit))
        } else {
            reviewRepository.findNewestReviewIds(storeId, PageRequest.of(0, limit))
        }

        val reviews = reviewRepository.findAllByIdInOrderByIdDesc(reviewIds)
        val reviewResDtos = mutableListOf<ReviewResDto>()
        for (review in reviews) {
            val tagIds = reviewTagRepository.findTagIdsWithReviewId(review.id)
            val imageIds = reviewImageRepository.findImageIdsWithReviewId(review.id)
            val tagResDtos = tagRepository.findAllById(tagIds)
                .map{ tag -> TagResDto(tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName)}
            val imageResDtos = imageRepository.findAllById(imageIds)
                .map{ image -> ReviewImageResDto(imageId = image.id, imageUrl = getImageUrl(image))}
            val thumbnailImageUrl = if(imageResDtos.isNotEmpty()) imageResDtos.first().imageUrl else null
            val imageCount = imageResDtos.size
            val reviewResDto = ReviewResDto(reviewId = review.id, reviewNickname = review.nickname,
                reviewContents = review.contents, reviewCreatedDatetime = review.createdDatetime,
                reviewTags = tagResDtos, thumbnailImageUrl = thumbnailImageUrl, imageCount = imageCount)
            reviewResDtos += reviewResDto
        }
        val lastId = if(reviewResDtos.isNotEmpty()) reviewResDtos.last().reviewId else null

        return ReviewsResDto(reviewResDtos, lastId)
    }

    override fun getReviewImages(storeId: Long, limit: Int): ReviewImagesResDto {
        val imageIds = reviewImageRepository.findNewestImageIds(storeId, PageRequest.of(0, limit))
        val imageResDtos = imageRepository.findAllByIdInOrderByIdDesc(imageIds)
            .map { image -> ReviewImageResDto(imageId = image.id, imageUrl = getImageUrl(image)) }

        return ReviewImagesResDto(imageResDtos)
    }

    override fun getReviewTags(storeId: Long): ReviewTagsResDto {
        println("ReviewServiceTestImpl.getReviewTags")
        val tagIds = reviewTagRepository.findTagIdsWithStoreId(storeId)
        val totalCnt = tagIds.size

        var lastTagId = 0L
        var lastTagCnt = 0
        val reviewTagResDtos = mutableListOf<ReviewTagResDto>()
        for (i in tagIds.indices) {
            if(lastTagId != tagIds[i]) {
                if(lastTagId != 0L) {
                    val tag = tagRepository.findById(lastTagId)
                        .orElseThrow { TagNotFoundException(lastTagId) }
                    val reviewTagResDto = ReviewTagResDto(
                        tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName, tagCount = lastTagCnt
                    )
                    reviewTagResDtos += reviewTagResDto
                }
                lastTagId = tagIds[i]
                lastTagCnt = 0
            }
            lastTagCnt ++
        }
        if(lastTagCnt != 0) {
            if(lastTagId != 0L) {
                val tag = tagRepository.findById(lastTagId)
                    .orElseThrow { TagNotFoundException(lastTagId) }
                val reviewTagResDto = ReviewTagResDto(
                    tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName, tagCount = lastTagCnt
                )
                reviewTagResDtos += reviewTagResDto
            }
        }

        return ReviewTagsResDto(
            totalCnt = totalCnt,
            tags = reviewTagResDtos
        )
    }

    private fun getImageUrl(image: Image): String {
        return "https://www.forefilm.com" + image.path
    }

}