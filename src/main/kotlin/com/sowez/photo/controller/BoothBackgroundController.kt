package com.sowez.photo.controller

import com.sowez.photo.dto.BoothBackgroundInfoResDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.service.BoothBackgroundService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/backgrounds")
class BoothBackgroundController(
        val boothBackgroundService: BoothBackgroundService
) {
    /**
     * 부스 배경색 조회
     */
    @GetMapping
    fun getBoothInfo(
            @PathVariable storeId: Long
    ): ResponseEntity<ResponseDto<BoothBackgroundInfoResDto>>{
        val boothBackgroundResponse = boothBackgroundService.getBoothBackgroundInfo(storeId)
        return ResponseEntity.ok(ResponseDto(body = boothBackgroundResponse))
    }
    
}