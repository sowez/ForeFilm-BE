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