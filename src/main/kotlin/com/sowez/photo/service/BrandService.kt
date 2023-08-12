package com.sowez.photo.service

import com.sowez.photo.dto.BrandInfoResDto
import org.springframework.stereotype.Service

@Service
interface BrandService {
    fun getBrandInfo(storeId: Long): BrandInfoResDto
}