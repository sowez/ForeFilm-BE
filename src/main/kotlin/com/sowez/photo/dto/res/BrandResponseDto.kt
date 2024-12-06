package com.sowez.photo.dto

import com.sowez.photo.entity.Image

data class BrandInfoResDto (
        val brandId: Long,
        val brandImageLogo : Image,
        val brandName: String
): SnakeCaseDto()

data class BrandInfosResDto(
        val brands : List<BrandInfoResDto> = mutableListOf()
): SnakeCaseDto()
