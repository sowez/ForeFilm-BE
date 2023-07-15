package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class StoreControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Test
    @DisplayName("새로운 매장 생성")
    fun create_new_store() {
        // given
        val requestDto = StoreCreateReqDto(
                storeName = "하루필름 강남점",
                storeType = StoreType.STORE,
                storeAddress = "서울 어쩌구 저쩌구",
                brandId = 11L,
                storeOperatingTime = "24시간 영업",
                storePhoneNum = "010-1234-5678",
                payTypes = listOf(PayType.CARD, PayType.CASH)
        )

        // when & then
        mockMvc.perform(
                post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.body.created").value(1L))
                .andDo(print())
    }

}