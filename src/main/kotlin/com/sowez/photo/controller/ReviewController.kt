package com.sowez.photo.controller

import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.service.ReviewService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
class ReviewController (val reviewService: ReviewService) {
    @PostMapping("/reviews")
    fun createReview (
        @RequestBody @Valid createDto:ReviewCreateReqDto
    ): ResponseEntity<ResponseDto<CreateResponseDto>>{
        val newReviewId = reviewService.createReview(createDto)
        return ResponseEntity.ok(ResponseDto(body = CreateResponseDto(newReviewId)))
    }

    @GetMapping("/reviews/{reviewId}")
    fun getSingleReview (
        @PathVariable reviewId: Long
    ): ResponseEntity<ResponseDto<SingleReviewResDto>>{
        val reviewResponse = reviewService.getSingleReview(reviewId)
        return ResponseEntity.ok(ResponseDto(body = reviewResponse))
    }

    @GetMapping("/stores/{storeId}/review-images")
    fun getReviewImages (
        @PathVariable storeId: Long
    ): ResponseEntity<ResponseDto<ReviewImagesResDto>>{
        val reviewResponse = reviewService.getReviewImages(storeId)
        return ResponseEntity.ok(ResponseDto(body = reviewResponse))
    }

    @GetMapping("/stores/{storeId}/reviews")
    fun getReviews (
        @PathVariable storeId: Long,
        @RequestParam(defaultValue = "10") limit: Int,
        @RequestParam offset: Int?
    ): ResponseEntity<ResponseDto<ReviewsResDto>> {
        val reviewResponse = reviewService.getReviews(storeId, limit, offset)
        return ResponseEntity.ok(ResponseDto(body = reviewResponse))
    }

    @GetMapping("/stores/{storeId}/tags")
    fun getReviewTags (
        @PathVariable storeId: Long
    ): ResponseEntity<ResponseDto<ReviewTagsResDto>>{
        val reviewResponse = reviewService.getReviewTags(storeId)
        return ResponseEntity.ok(ResponseDto(body = reviewResponse))
    }
}