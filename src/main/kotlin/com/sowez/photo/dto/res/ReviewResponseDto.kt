package com.sowez.photo.dto.res
import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.entity.Tag
import java.time.LocalDateTime
import java.util.*

// 리뷰 사진 조회 시 보여지는 Review
data class SingleReviewResDto(
    val reviewNickname: String,
    val reviewContents: String,
    val reviewCreatedDatetime: LocalDateTime,
    val reviewTags: List<TagResDto>? = mutableListOf(),
    val reviewImages: List<ReviewImageResDto>? = mutableListOf(),
): SnakeCaseDto()

// 리뷰 리스트를 위한 Review
data class ReviewResDto(
    val reviewId: Long,
    val reviewNickname: String,
    val reviewContents: String,
    val reviewCreatedDatetime: LocalDateTime,
    val reviewTags: List<TagResDto>? = mutableListOf(),
    val thumbnailImageUrl: String? = null,
    val imageCount: Int,
): SnakeCaseDto()

data class ReviewsResDto (
    val reviews: List<ReviewResDto> = mutableListOf(),
    val lastReviewId: Long? = null
): SnakeCaseDto()


// Count가 포함된 review tag
data class ReviewTagResDto (
    val tagId: Long,
    val tagContents: String,
    val tagEmojiName: String,
    val tagCount: Int
): SnakeCaseDto()

// Count가 포함된 review tag list
data class ReviewTagsResDto (
    val totalCnt: Int,
    val tags: List<ReviewTagResDto> = mutableListOf()
): SnakeCaseDto()

data class ReviewImageResDto (
    val imageId: Long,
    val imageUrl: String
): SnakeCaseDto()

data class ReviewImagesResDto (
    val images: List<ReviewImageResDto> = mutableListOf()
): SnakeCaseDto()
