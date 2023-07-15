package com.sowez.photo.service

import com.sowez.photo.dto.req.TagCreateReqDto
import com.sowez.photo.dto.res.*
import org.springframework.stereotype.Service

@Service
interface TagService {
    fun createTag(createDto: TagCreateReqDto): Long
    fun getTags(): TagsResDto
}