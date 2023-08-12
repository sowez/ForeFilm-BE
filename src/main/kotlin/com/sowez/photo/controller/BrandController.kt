package com.sowez.photo.controller

import com.sowez.photo.dto.BoothInfoResDto
import com.sowez.photo.dto.BrandInfoResDto
import com.sowez.photo.dto.BrandsResponseDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brands")
class BrandController(
        val brandService: BrandService
) {
    /**
     * 브랜드 리스트 조회
     */
    @GetMapping
    fun getBoothInfo(
    ): ResponseEntity<ResponseDto<BrandsResponseDto>>{
        val brandsResponse = brandService.getBrandInfo()
        return ResponseEntity.ok(ResponseDto(body = brandsResponse))
    }
    
}