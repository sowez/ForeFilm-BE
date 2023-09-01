package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.req.StoreEditReqDto
import com.sowez.photo.entity.Address
import com.sowez.photo.entity.Brand
import com.sowez.photo.entity.Image
import com.sowez.photo.entity.Store
import com.sowez.photo.repository.BrandRepository
import com.sowez.photo.repository.ImageRepository
import com.sowez.photo.repository.StoreRepository
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import org.junit.jupiter.api.Assertions
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
import java.util.UUID

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class StoreControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val storeRepository: StoreRepository,
    @Autowired val brandRepository: BrandRepository,
    @Autowired val imageRepository: ImageRepository
) {

    @Test
    @DisplayName("새로운 매장 생성")
    fun create_new_store() {
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

        val requestDto = StoreCreateReqDto(
                storeName = "하루필름 강남점",
                storeType = StoreType.STORE,
                storeAddress = "서울 어쩌구 저쩌구",
                brandId = brand.id,
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
                .andDo(print())
    }

    @Test
    @DisplayName("새로운 매장 생성 (필수 항목만 포함)")
    fun create_new_store_with_only_required_fields() {
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
        val requestDto = StoreCreateReqDto(
                storeName = "하루필름 강남점",
                storeType = StoreType.STORE,
                storeAddress = "서울 어쩌구 저쩌구",
                brandId = brand.id,
                storeOperatingTime = null,
                storePhoneNum = null,
                payTypes = null
        )

        // when & then
        mockMvc.perform(
                post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        )
                .andExpect(status().isOk)
                .andDo(print())
    }

    @Test
    @DisplayName("매장 정보 수정")
    fun edit_store_info() {
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

        val requestDto = StoreEditReqDto(
            storeName = "하루필름 강남점",
            storeType = StoreType.STORE,
            storeAddress = "서울 어쩌구 저쩌구",
            brandId = brand.id,
            storeOperatingTime = "24시간 영업. 연중무휴",
            storePhoneNum = "010-1234-5677",
            payTypes = listOf(PayType.CASH)
        )

        // when & then
        mockMvc.perform(
            patch("/stores/{storeId}", store.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        )
            .andExpect(status().isOk)
            .andDo(print())

        val editedStore = storeRepository.findById(store.id).get()
        assertEquals(requestDto.storeName, editedStore.name)
        assertEquals(requestDto.storeType, editedStore.type)
        assertEquals(requestDto.storeAddress, editedStore.addressInfo.address)
        assertEquals(requestDto.brandId, editedStore.brand.id)
        assertEquals(requestDto.storeOperatingTime, editedStore.operatingTime)
        assertEquals(requestDto.storePhoneNum, editedStore.phoneNumber)
        assertEquals(requestDto.payTypes, editedStore.getPayTypes().stream().toList())
    }

    @Test
    @DisplayName("매장 정보 수정 (필수 항목만 포함)")
    fun edit_store_info_with_only_required_fields() {
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

        val requestDto = StoreEditReqDto(
            storeName = "하루필름 강남점",
            storeType = StoreType.STORE,
            storeAddress = "서울 어쩌구 저쩌구",
            brandId = brand.id,
            storeOperatingTime = null,
            storePhoneNum = null,
            payTypes = null
        )

        // when & then
        mockMvc.perform(
            patch("/stores/{storeId}", store.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        )
            .andExpect(status().isOk)
            .andDo(print())

        val editedStore = storeRepository.findById(store.id).get()
        assertEquals(requestDto.storeName, editedStore.name)
        assertEquals(requestDto.storeType, editedStore.type)
        assertEquals(requestDto.storeAddress, editedStore.addressInfo.address)
        assertEquals(requestDto.brandId, editedStore.brand.id)
        assertNull(editedStore.operatingTime)
        assertNull(editedStore.phoneNumber)
        assertNull(editedStore.payTypes)
    }

    @Test
    @DisplayName("매장 정보 조회")
    fun get_store_info() {
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

        // when & then
        mockMvc.perform(
            get("/stores/{storeId}", store.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.store_name").value("하루필름 강남점"))
            .andExpect(jsonPath("$.body.store_type").value(StoreType.STORE.name))
            .andExpect(jsonPath("$.body.store_address").value("서울 어쩌구 저쩌구"))
            .andExpect(jsonPath("$.body.brand_id").value(brand.id))
            .andExpect(jsonPath("$.body.store_operating_time").value("24시간 영업"))
            .andExpect(jsonPath("$.body.store_phone_num").value("010-1234-5678"))
            .andExpect(jsonPath("$.body.pay_types[0]").value(PayType.CARD.name))
            .andExpect(jsonPath("$.body.pay_types[1]").value(PayType.CASH.name))
            .andDo(print())
    }

    @Test
    @DisplayName("매장 검색")
    fun search_store() {
        // when & then
        mockMvc.perform(
            get("/stores?q=강남")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.content[0].store_id").value(1))
            .andExpect(jsonPath("$.body.content[0].store_name").value("하루필름 강남점"))
            .andExpect(jsonPath("$.body.content[0].store_address").value("여기저기"))
            .andExpect(jsonPath("$.body.content[0].review_cnt").value(101))
            .andExpect(jsonPath("$.body.content[1].store_id").value(4))
            .andExpect(jsonPath("$.body.content[1].store_name").value("하루필름 강남2호점"))
            .andExpect(jsonPath("$.body.content[1].store_address").value("요기조기"))
            .andExpect(jsonPath("$.body.content[1].review_cnt").value(79))
            .andDo(print())
    }

    @Test
    @DisplayName("매장의 브랜드 이미지 조회")
    fun get_store_brand_image() {
        // when & then
        mockMvc.perform(
            get("/stores/{storeId}/brand-logo-image", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.brand_id").value(10L))
            .andExpect(jsonPath("$.body.brand_logo_image_id").value(100L))
            .andExpect(jsonPath("$.body.image_url").value("https://www.forefilm.com/images/123"))
            .andDo(print())
    }

    @Test
    @DisplayName("매장 사진 리스트 조회")
    fun get_store_images() {
        // when & then
        mockMvc.perform(
            get("/stores/{storeId}/images?limit=20&offset=1", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images[0].image_id").value(1L))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com/images/1234"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(5L))
            .andExpect(jsonPath("$.body.images[1].image_url").value("https://www.forefilm.com/images/4321"))
            .andExpect(jsonPath("$.body.images[2].image_id").value(10L))
            .andExpect(jsonPath("$.body.images[2].image_url").value("https://www.forefilm.com/images/43"))
            .andExpect(jsonPath("$.body.last_image_id").value(10L))
            .andDo(print())
    }

    @Test
    @DisplayName("매장 사진 리스트 조회 (limit, offset 정보 없음)")
    fun get_store_images_no_param() {
        // when & then
        mockMvc.perform(
            get("/stores/{storeId}/images", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images[0].image_id").value(1L))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com/images/1234"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(5L))
            .andExpect(jsonPath("$.body.images[1].image_url").value("https://www.forefilm.com/images/4321"))
            .andExpect(jsonPath("$.body.images[2].image_id").value(10L))
            .andExpect(jsonPath("$.body.images[2].image_url").value("https://www.forefilm.com/images/43"))
            .andExpect(jsonPath("$.body.last_image_id").value(10L))
            .andDo(print())
    }

}