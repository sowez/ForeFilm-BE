package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.ReviewCreateReqDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
){
    @Test
    @DisplayName("새로운 리뷰 작성")
    fun create_new_review(){
        //given
        val review = ReviewCreateReqDto(
            reviewNickname = "eumji",
            reviewPassword = "123",
            reviewContents = "123",
            reviewTagIds = listOf(1L),
            reviewImages = listOf("1")
        )

        // when & then
        mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(review))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.created").value(1L))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("리뷰 한개 조회")
    fun get_single_review(){
        // when & then
        mockMvc.perform(
            get("/reviews/{review_id}", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.review_nickname").value("eumji"))
            .andExpect(jsonPath("$.body.review_profile").value(1))
            .andExpect(jsonPath("$.body.review_contents").value(1))
//            .andExpect(jsonPath("$.body.review_created_datetime").value(Date(System.currentTimeMillis())))
            .andExpect(jsonPath("$.body.tags[0].tag_id").value(1))
            .andExpect(jsonPath("$.body.tags[0].tag_contents").value("tag1"))
            .andExpect(jsonPath("$.body.tags[0].tag_emoji_name").value("emoji1"))
            .andExpect(jsonPath("$.body.tags[1].tag_id").value(2))
            .andExpect(jsonPath("$.body.tags[1].tag_contents").value("tag2"))
            .andExpect(jsonPath("$.body.tags[1].tag_emoji_name").value("emoji2"))
            .andExpect(jsonPath("$.body.image_url").value("jh"))
    }

    @Test
    @DisplayName("리뷰 이미지 조회")
    fun get_review_images(){
        // when & then
        mockMvc.perform(
            get("/stores/{store_id}/review-images", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images[0].image_id").value(1))
            .andExpect(jsonPath("$.body.images[0].image_url").value("image1"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(2))
            .andExpect(jsonPath("$.body.images[1].image_url").value("image2"))
    }

    @Test
    @DisplayName("스토어 리뷰 조회")
    fun get_reviews(){
        // when & then
        mockMvc.perform(
            get("/stores/{storeId}/reviews?limit=20&offset=1", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.reviews[0].review_id").value(1))
            .andExpect(jsonPath("$.body.reviews[0].profile_image_url").value("profileUrl"))
//            .andExpect(jsonPath("$.body.reviews[0].created_datetime").value(1))
            .andExpect(jsonPath("$.body.reviews[0].contents").value("content"))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_id").value(1))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_contents").value("1"))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_emoji_name").value("1"))
            .andExpect(jsonPath("$.body.reviews[0].thumbnail_image_url").value("url"))
            .andExpect(jsonPath("$.body.reviews[0].image_count").value(1))
    }

    @Test
    @DisplayName("스토어 태그 조회")
    fun get_review_tags(){
        // when && then
        mockMvc.perform(
            get("/stores/{storeId}/tags",1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.total_cnt").value(10))
            .andExpect(jsonPath("$.body.tags[0].tag_id").value(1))
            .andExpect(jsonPath("$.body.tags[0].tag_contents").value("1"))
            .andExpect(jsonPath("$.body.tags[0].tag_emoji_name").value("1"))
            .andExpect(jsonPath("$.body.tags[0].tag_count").value(1))
    }
}