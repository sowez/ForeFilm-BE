package com.sowez.photo.error

class StoreNotFoundException(
    val storeId: Long
): CustomException(
    errorCode = ErrorCode.STORE_NOT_EXIST,
    msg = "store_id: $storeId")

class BrandNotFoundException(
    val brandId: Long
): CustomException(
    errorCode = ErrorCode.BRAND_NOT_EXIST,
    msg = "brand_id: $brandId")

class ReviewNotFoundException(
    val reviewId: Long
): CustomException(
    errorCode = ErrorCode.REVIEW_NOT_EXIST,
    msg = "review_id: $reviewId")

class TagNotFoundException(
    val tagId: Long
): CustomException(
    errorCode = ErrorCode.TAG_NOT_EXIST,
    msg = "tag_id: $tagId")