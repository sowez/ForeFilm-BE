package com.sowez.photo.service

import com.sowez.photo.dto.req.TagCreateReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.entity.*
import com.sowez.photo.repository.TagRepository
import org.springframework.stereotype.Service

@Service
interface TagService {
    fun createTag(createDto: TagCreateReqDto): Long
    fun getTags(): TagsResDto
}

@Service
class TagServiceTestImpl(
    val tagRepository: TagRepository
): TagService {
    override fun createTag(createDto: TagCreateReqDto): Long {
        println("TagServiceTestImpl.createTag")
        val tag = tagRepository.save(
            Tag(
                contents = createDto.tagContents,
                emojiName = createDto.tagEmojiName
            )
        )
        return tag.id
    }

    override fun getTags(): TagsResDto {
        println("TagServiceTestImpl.getTags")
        val tagResDtos = tagRepository.findAll()
            .map { tag -> TagResDto(tagId = tag.id, tagContents = tag.contents, tagEmojiName = tag.emojiName)}
        return TagsResDto(tagResDtos)
    }
}