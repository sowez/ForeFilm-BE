package com.sowez.photo.service

import com.sowez.photo.dto.req.TagCreateReqDto
import com.sowez.photo.dto.res.*
import org.springframework.stereotype.Service

@Service
interface TagService {
    fun createTag(createDto: TagCreateReqDto): Long
    fun getTags(): TagsResDto
}

@Service
class TagServiceTestImpl: TagService {
    override fun createTag(createDto: TagCreateReqDto): Long {
        TODO("Not yet implemented")
    }

    override fun getTags(): TagsResDto {
        TODO("Not yet implemented")
    }
}
