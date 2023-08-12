package com.sowez.photo.dto

data class BrandInfoResDto (
        val brandId: Long,
        val brandName: String
): SnakeCaseDto()

data class BrandsResponseDto (
    val brands: List<BrandInfoResDto> = mutableListOf()
): SnakeCaseDto()