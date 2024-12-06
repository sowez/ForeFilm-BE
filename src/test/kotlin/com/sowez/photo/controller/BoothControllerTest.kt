package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.BoothCreateReqDto
import com.sowez.photo.dto.req.BoothEditReqDto
import com.sowez.photo.entity.*
import com.sowez.photo.repository.*
import com.sowez.photo.type.DownloadType
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import org.hibernate.internal.util.collections.CollectionHelper.listOf
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
import org.springframework.transaction.annotation.Transactional
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
internal class BoothControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @Autowired val imageRepository: ImageRepository,
        @Autowired val brandRepository: BrandRepository,
        @Autowired val storeRepository: StoreRepository,
        @Autowired val boothBackgroundColorRepository: BoothBackgroundColorRepository,
        @Autowired val storeBoothBackgroundColorRepository: StoreBoothBackgroundColorRepository


) {

    @Test
    @DisplayName("부스 생성")
    fun createBooth() {
        // given
        val image = imageRepository.save(
                Image(
                        uuid = UUID.randomUUID().toString(),
                        originalName = "image.jpg",
                        name = "image",
                        extension = "jpg",
                        path = "/image"
                )
        )

        val brand = brandRepository.save(
                Brand(
                        logoImage = image,
                        name = "하루필름"
                )
        )

        val store = storeRepository.save(
                Store(
                        name = "하루필름 강남점",
                        type = StoreType.STORE,
                        addressInfo = Address(address = "서울 어쩌구 저쩌구"),
                        brand = brand,
                        operatingTime = "24시간 영업",
                        phoneNumber = "010-1234-5678",
                        payTypes = listOf(PayType.CARD, PayType.CASH)
                )
        )


        val booth = BoothCreateReqDto(
                boothCount = 3,
                boothBackgroundColorIds = listOf(1, 2, 3),
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
                MockMvcRequestBuilders.post("/stores/{storeId}/booth", store.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booth))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("부스 수정")
    fun editBoothInfo() {
        val image = imageRepository.save(
                Image(
                        uuid = UUID.randomUUID().toString(),
                        originalName = "image.jpg",
                        name = "image",
                        extension = "jpg",
                        path = "/image"
                )
        )

        val brand = brandRepository.save(
                Brand(
                        logoImage = image,
                        name = "하루필름"
                )
        )

        val store = storeRepository.save(
                Store(
                        name = "하루필름 강남점",
                        type = StoreType.STORE,
                        addressInfo = Address(address = "서울 어쩌구 저쩌구"),
                        brand = brand,
                        operatingTime = "24시간 영업",
                        phoneNumber = "010-1234-5678",
                        payTypes = listOf(PayType.CARD, PayType.CASH),
                )
        )

        val booth = BoothEditReqDto(
                boothCount = 4,
                boothBackgroundColorIds = listOf(1, 2, 3),
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
                MockMvcRequestBuilders.patch("/stores/{storeId}/booth", store.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booth))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("부스 정보 조회")
    fun getBoothInfo() {
        val image = imageRepository.save(
                Image(
                        uuid = UUID.randomUUID().toString(),
                        originalName = "image.jpg",
                        name = "image",
                        extension = "jpg",
                        path = "/image"
                )
        )

        val brand = brandRepository.save(
                Brand(
                        logoImage = image,
                        name = "하루필름"
                )
        )

        val boothInfo = BoothInfo(
                boothCount = 3,
                minPeopleCount = 1,
                maxPeopleCount = 6,
                downloadPeriod = "7",
                isReshoot = true,
                isRemote = true,
                isCurlingIron = true,
                isEnvelope = true,
                isFootrest = true
        )

        boothInfo.setDownloadTypes(listOf(DownloadType.QR, DownloadType.APP))

        val store = storeRepository.save(
                Store(
                        name = "하루필름 강남점",
                        type = StoreType.STORE,
                        addressInfo = Address(address = "서울 어쩌구 저쩌구"),
                        brand = brand,
                        operatingTime = "24시간 영업",
                        phoneNumber = "010-1234-5678",
                        payTypes = listOf(PayType.CARD, PayType.CASH),
                        boothInfo = boothInfo
                )
        )

        val boothBackgroundColor = boothBackgroundColorRepository.save(
                BoothBackgroundColor(
                        name = "분홍",
                        code = "#ffffff"
                )
        )

        val storeBoothBackgroundColor = storeBoothBackgroundColorRepository.save(
                StoreBoothBackgroundColor(
                        store = store,
                        boothBackgroundColor = boothBackgroundColor
                )
        )

        val boothBackgroundColor2 = boothBackgroundColorRepository.save(
                BoothBackgroundColor(
                        name = "파랑",
                        code = "#ffffff"
                )
        )

        val storeBoothBackgroundColor2 = storeBoothBackgroundColorRepository.save(
                StoreBoothBackgroundColor(
                        store = store,
                        boothBackgroundColor = boothBackgroundColor2
                )
        )


        mockMvc.perform(
                MockMvcRequestBuilders.get("/stores/{storeId}/booth", store.id)
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_count").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[0].booth_background_color_name").value("분홍"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.booth_background_colors[1].booth_background_color_name").value("파랑"))
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