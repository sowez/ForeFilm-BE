package com.sowez.photo.dto

data class BrandInfoResDto (
        val brandId: Long,
        val brandLogoImageId: Long,
        val brandName: String
): SnakeCaseDto()