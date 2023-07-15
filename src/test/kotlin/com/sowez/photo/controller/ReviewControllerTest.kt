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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
    @DisplayName("")
    fun create_new_review(){
        val review = ReviewCreateReqDto(
            reviewNickname = "eumji",
            reviewPassword = "123",
            reviewContents = "123",
            reviewTagIds = listOf(1L),
            reviewImages = listOf("1")
        )

        mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(review))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.created").value(1L))
            .andDo(MockMvcResultHandlers.print())
    }
}