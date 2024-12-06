package com.sowez.photo.service

import com.sowez.photo.dto.BrandInfoResDto
import com.sowez.photo.dto.BrandInfosResDto
import com.sowez.photo.repository.BrandRepository
import org.springframework.stereotype.Service

@Service
interface BrandService {
    fun getBrandInfo(): BrandInfosResDto
}

@Service
class BrandServiceImpl(
        val brandRepository: BrandRepository
) : BrandService {
    override fun getBrandInfo(): BrandInfosResDto {
        val brands = brandRepository.findAll()
        return BrandInfosResDto(brands.map { brand ->
            BrandInfoResDto(
                    brandId = brand.id,
                    brandName = brand.name,
                    brandImageLogo = brand.logoImage
            )
        })
    }
}