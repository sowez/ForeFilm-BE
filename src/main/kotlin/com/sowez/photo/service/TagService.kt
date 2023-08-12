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
        println("TagServiceTestImpl.createTag")
        return 1L
    }

    override fun getTags(): TagsResDto {
        println("TagServiceTestImpl.getTags")
        return TagsResDto(
            tags = listOf(
                TagResDto(1,"1","1")
            )
        )
    }
}
