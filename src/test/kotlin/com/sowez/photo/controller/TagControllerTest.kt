package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.TagCreateReqDto
import com.sowez.photo.entity.*
import com.sowez.photo.repository.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class TagControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val tagRepository: TagRepository,
) {
    @Test
    @DisplayName("새로운 태그 추가")
    fun create_tag(){
        //given
        val tag = TagCreateReqDto(
            tagContents = "깔끔해요",
            tagEmojiName = "CLEAN"
        )

        // when & then
        mockMvc.perform(
            post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag))
        )
            .andExpect(status().isOk)
            .andDo(print())
    }
    @Test
    @DisplayName("태그 리스트 조회")
    fun get_tags(){
        //given
        val tag1 = tagRepository.save(
            Tag(
                contents= "조명이 밝아요",
                emojiName= "LIGHT"
            )
        )

        val tag2 = tagRepository.save(
            Tag(
                contents= "깨끗해요",
                emojiName= "CLEAN"
            )
        )

        // when & then
        mockMvc.perform(
            get("/tags")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.tags[0].tag_id").value(tag1.id))
            .andExpect(jsonPath("$.body.tags[0].tag_contents").value(tag1.contents))
            .andExpect(jsonPath("$.body.tags[0].tag_emoji_name").value(tag1.emojiName))
            .andExpect(jsonPath("$.body.tags[1].tag_id").value(tag2.id))
            .andExpect(jsonPath("$.body.tags[1].tag_contents").value(tag2.contents))
            .andExpect(jsonPath("$.body.tags[1].tag_emoji_name").value(tag2.emojiName))
            .andDo(print())
    }
}