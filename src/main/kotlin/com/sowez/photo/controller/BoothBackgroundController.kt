package com.sowez.photo.controller

import com.sowez.photo.dto.BoothBackgroundInfoResDto
import com.sowez.photo.dto.BoothBackgroundsResDto
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
    ): ResponseEntity<ResponseDto<BoothBackgroundsResDto>>{
        val boothBackgroundsResponse = boothBackgroundService.getBoothBackgroundInfo()
        return ResponseEntity.ok(ResponseDto(body = boothBackgroundsResponse))
    }
    
}