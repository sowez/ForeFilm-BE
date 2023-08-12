package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
internal class BrandControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper
) {

    @Test
    fun getBoothInfo() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[0].brand_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[0].brand_name").value("하루필름"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[1].brand_id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[1].brand_name").value("인생네컷"))
                .andDo(MockMvcResultHandlers.print())

    }
}