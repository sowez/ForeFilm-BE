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
internal class BoothBackgroundControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper
) {

    @Test
    fun getBoothInfo() {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/backgrounds")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_name").value("흰색"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_code").value("#FFFFFF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_name").value("하늘색"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_code").value("#A1B1C1"))

                .andDo(MockMvcResultHandlers.print())
    }
}