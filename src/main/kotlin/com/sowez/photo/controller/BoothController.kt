package com.sowez.photo.controller

import com.sowez.photo.dto.BoothInfoResDto
import com.sowez.photo.dto.req.BoothCreateReqDto
import com.sowez.photo.dto.req.BoothEditReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.service.BoothService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stores")
class BoothController(
        val boothService: BoothService
) {
    /**
     * 부스 정보 등록
     */
    @PostMapping("/{storeId}/booth")
    fun createBooth(
            @RequestBody @Valid createDto: BoothCreateReqDto
    ): ResponseEntity<ResponseDto<CreateResponseDto>> {
        val newBootheId = boothService.createBooth(createDto)
        return ResponseEntity.ok(
                ResponseDto(body = CreateResponseDto(newBootheId))
        )
    }

    /**
     * 부스 정보 수정
     */
    @PatchMapping("/{storeId}/booth")
    fun editBoothInfo(
            @PathVariable storeId: Long,
            @RequestBody @Valid editDto: BoothEditReqDto
    ): ResponseEntity<ResponseDto<Void>>{
        boothService.editBoothInfo(storeId)
        return ResponseEntity.ok(ResponseDto())
    }

    /**
     * 부스 정보 조회
     */
    @GetMapping("/{storeId}/booth")
    fun getBoothInfo(
            @PathVariable boothId: Long
    ): ResponseEntity<ResponseDto<BoothInfoResDto>>{
        val boothResponse = boothService.getBoothInfo(boothId)
        return ResponseEntity.ok(ResponseDto(body = boothResponse))
    }
    
}