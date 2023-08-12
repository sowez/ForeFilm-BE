package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.BoothCreateReqDto
import com.sowez.photo.dto.req.BoothEditReqDto
import com.sowez.photo.type.DownloadType
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
internal class BoothControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper
) {

    @Test
    @DisplayName("부스 생성")
    fun createBooth() {
        val booth = BoothCreateReqDto(
            boothCount = 3,
            boothBackgroundColorName = listOf("분홍", "파랑", "회색"),
            layoutImagesIds = listOf(1),
            minPeopleCount = 1,
            maxPeopleCount = 6,
            downloadTypes = listOf(DownloadType.QR, DownloadType.APP),
            downloadPeriod = "7",
            isReshoot = true,
            isRemote = true,
            isCurlingIron = true,
            isEnvelope = true,
            isFootrest = true
        )

        mockMvc.perform(
                MockMvcRequestBuilders.post("/stores/{storeId}/booth",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booth))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.created").value(1L))
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("부스 수정")
    fun editBoothInfo() {
        val booth = BoothEditReqDto(
                boothCount = 4,
                boothBackgroundColorName = listOf("분홍", "파랑", "회색", "흰색"),
                layoutImagesIds = listOf(1),
                minPeopleCount = 1,
                maxPeopleCount = 6,
                downloadTypes = listOf(DownloadType.QR, DownloadType.APP),
                downloadPeriod = "7",
                isReshoot = true,
                isRemote = true,
                isCurlingIron = true,
                isEnvelope = true,
                isFootrest = true
        )

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/stores/{storeId}/booth",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booth))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("부스 정보 조회")
    fun getBoothInfo() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/stores/{storeId}/booth",1)
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_count").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_color_name[0]").value("분홍"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_color_name[1]").value("파랑"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_color_name[2]").value("회색"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.layout_image_urls[0]").value("forefilm.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.min_people_count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.max_people_count").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.download_types[0]").value(DownloadType.QR.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.download_types[1]").value(DownloadType.APP.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.download_period").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.is_reshoot").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.is_remote").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.is_curling_iron").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.is_envelope").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.is_footrest").value(true))
                .andDo(MockMvcResultHandlers.print())
    }
}