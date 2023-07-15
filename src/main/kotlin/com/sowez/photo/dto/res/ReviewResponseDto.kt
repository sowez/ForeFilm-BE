package com.sowez.photo.dto.res
import com.sowez.photo.dto.SnakeCaseDto
import java.util.Date

// 리뷰 사진 조회 시 보여지는 Review
class SingleReviewResDto (
        val reviewNickname: String,
        val reviewProfile: String,
        val reviewCreatedDatetime: Date,
        val reviewContents: String,
        val tags: List<TagResDto> = mutableListOf(),
        val imageUrl: String
): SnakeCaseDto()

// 리뷰 리스트를 위한 Review
class ReviewResDto (
    val reviewId: Long,
    val profileImageUrl: String,
    val createdDatetime: Date,
    val contents: String,
    val tags: List<TagResDto> = mutableListOf(),
    val thumbnailImageUrl: String,
    val imageCount: Int
): SnakeCaseDto()

data class ReviewsResDto (
    val reviews: List<ReviewResDto> = mutableListOf()
): SnakeCaseDto()

data class ReviewImageResDto (
    val imageId: Long,
    val imageUrl: String
): SnakeCaseDto()

data class ReviewImagesResDto (
    val images: List<ReviewImageResDto> = mutableListOf()
): SnakeCaseDto()

// Count가 포함된 review tag
data class ReviewTagResDto (
    val tagId: Int,
    val tagContents: String,
    val tagEmojiName: String,
    val tagCount: Int
): SnakeCaseDto()

// Count가 포함된 review tag list
data class ReviewTagsResDto (
    val totalCnt: Int,
    val tags: List<ReviewTagResDto> = mutableListOf()
): SnakeCaseDto()

