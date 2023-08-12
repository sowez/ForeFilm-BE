package com.sowez.photo.service

import com.sowez.photo.dto.BoothBackgroundInfoResDto
import com.sowez.photo.dto.BrandInfoResDto
import com.sowez.photo.dto.BrandsResponseDto
import org.springframework.stereotype.Service

@Service
interface BrandService {
    fun getBrandInfo(): BrandsResponseDto
}

@Service
class BrandServiceTestImpl : BrandService {
    override fun getBrandInfo(): BrandsResponseDto {
        return BrandsResponseDto(
                listOf(
                        BrandInfoResDto(brandId = 1, brandName = "하루필름"),
                        BrandInfoResDto(brandId = 2, brandName = "인생네컷")
                )
        )
    }

}