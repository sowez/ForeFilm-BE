package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.entity.BoothBackgroundColor
import com.sowez.photo.repository.BoothBackgroundColorRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
internal class BoothBackgroundControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @Autowired val boothBackgroundColorRepository: BoothBackgroundColorRepository
) {

    @Test
    fun getBoothBackgroundInfo() {

        val boothBackgroundColor = boothBackgroundColorRepository.save(
                BoothBackgroundColor(
                        name = "흰색",
                        code = "#FFFFFF"
                )
        )
        val boothBackgroundColor2 = boothBackgroundColorRepository.save(
                BoothBackgroundColor(
                        name = "하늘색",
                        code = "#A1B1C1"
                )
        )
        mockMvc.perform(
                MockMvcRequestBuilders.get("/backgrounds")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_id").value(boothBackgroundColor.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_name").value("흰색"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_code").value("#FFFFFF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_id").value(boothBackgroundColor2.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_name").value("하늘색"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_code").value("#A1B1C1"))

                .andDo(MockMvcResultHandlers.print())
    }
}