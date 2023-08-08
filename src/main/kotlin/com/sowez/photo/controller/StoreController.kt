package com.sowez.photo.controller

import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.req.StoreEditReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.service.StoreService
import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stores")
class StoreController(
    val storeService: StoreService
) {

    @PostMapping
    fun createStore(
        @RequestBody @Valid createDto: StoreCreateReqDto
    ): ResponseEntity<ResponseDto<CreateResponseDto>> {
        val newStoreId = storeService.createStore(createDto)
        return ResponseEntity.ok(
            ResponseDto(body = CreateResponseDto(newStoreId))
        )
    }

    @PatchMapping("/{storeId}")
    fun editStoreInfo(
        @PathVariable storeId: Long,
        @RequestBody @Valid editDto: StoreEditReqDto
    ): ResponseEntity<ResponseDto<Void>>{
        storeService.editStoreInfo(storeId)
        return ResponseEntity.ok(ResponseDto())
    }

    @GetMapping("/{storeId}")
    fun getStoreInfo(
        @PathVariable storeId: Long
    ): ResponseEntity<ResponseDto<StoreInfoResDto>>{
        val storeResponse = storeService.getStoreInfo(storeId)
        return ResponseEntity.ok(ResponseDto(body = storeResponse))
    }

    @GetMapping
    fun searchStore(
        @RequestParam(value = "q") query: String,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<ResponseDto<Page<StoreSearchResDto>>> {
        val storeResponses = storeService.searchStore(query, pageable)
        return ResponseEntity.ok(ResponseDto(body = storeResponses))
    }

    @GetMapping("/{storeId}/brand-logo-image")
    fun getStoreBrandImage(
        @PathVariable storeId: Long
    ): ResponseEntity<ResponseDto<StoreBrandLogoImageResDto>>{
        val brandLogoImageResponse = storeService.getBrandLogoImage(storeId)
        return ResponseEntity.ok(ResponseDto(body = brandLogoImageResponse))
    }

    @GetMapping("/{storeId}/images")
    fun getStoreImages(
        @PathVariable storeId: Long,
        @RequestParam limit: Int,
        @RequestParam offset: Int
    ): ResponseEntity<ResponseDto<StoreImagesResDto>> {
        val storeResponses = storeService.getStoreImages(storeId, limit, offset)
        return ResponseEntity.ok(ResponseDto(body = storeResponses))
    }

}