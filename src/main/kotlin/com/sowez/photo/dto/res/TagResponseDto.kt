package com.sowez.photo.dto.res

import com.fasterxml.jackson.annotation.JsonInclude
import com.sowez.photo.dto.SnakeCaseDto

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TagResDto (
    val tagId: Long,
    val tagContents: String,
    val tagEmojiName: String
): SnakeCaseDto()

data class TagsResDto (
    val tags: List<TagResDto> = mutableListOf()
): SnakeCaseDto()