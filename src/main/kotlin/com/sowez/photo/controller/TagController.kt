package com.sowez.photo.controller

import com.sowez.photo.dto.req.TagCreateReqDto
import com.sowez.photo.service.TagService
import org.springframework.web.bind.annotation.RestController
import com.sowez.photo.dto.res.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/tags")
class TagController (val tagService: TagService){
    @PostMapping
    fun createTag(
            @RequestBody @Valid createDto: TagCreateReqDto
    ): ResponseEntity<ResponseDto<CreateResponseDto>>{
        val newTagId = tagService.createTag(createDto)
        return ResponseEntity.ok(ResponseDto(body = CreateResponseDto(newTagId)))
    }

    @GetMapping
    fun getTags(): ResponseEntity<ResponseDto<TagsResDto>>{
        val tagResponse = tagService.getTags()
        return ResponseEntity.ok(ResponseDto(body = tagResponse))
    }
}