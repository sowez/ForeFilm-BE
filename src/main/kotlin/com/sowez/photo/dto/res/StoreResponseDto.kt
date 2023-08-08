package com.sowez.photo.dto.res
import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType

data class StoreInfoResDto (
    val storeName: String,
    val storeType: StoreType,
    val storeAddress: String,
    val brandId: Long,
    val storeOperatingTime: String? = null,
    val storePhoneNum: String? = null,
    val payTypes: List<PayType> = mutableListOf()
): SnakeCaseDto()

data class StoreSearchResDto (
    val storeId: Long,
    val storeName: String,
    val storeAddress: String,
    val reviewCnt: Int
): SnakeCaseDto()

data class StoreBrandLogoImageResDto (
    val brandId: Long,
    val brandLogoImageId: Long,
    val imageUrl: String
): SnakeCaseDto()

data class StoreImagesResDto (
    val images: List<StoreImageResDto> = mutableListOf(),
    val lastImageId: Long? = null
): SnakeCaseDto()

data class StoreImageResDto (
    val imageId: Long,
    val imageUrl: String
): SnakeCaseDto()

