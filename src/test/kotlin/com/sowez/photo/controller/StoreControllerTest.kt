package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.req.StoreEditReqDto
import com.sowez.photo.entity.*
import com.sowez.photo.repository.*
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
    @Autowired val imageRepository: ImageRepository,
    @Autowired val reviewRepository: ReviewRepository,
    @Autowired val reviewImageRepository: ReviewImageRepository
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

        val store1 = storeRepository.save(
            Store(
                name = "하루필름 강남점",
                type = StoreType.STORE,
                addressInfo = Address(address = "여기저기"),
                brand = brand,
                operatingTime = "24시간 영업",
                phoneNumber = "014-1234-5678",
                payTypes = listOf(PayType.CARD, PayType.CASH)
            )
        )

        val store2 = storeRepository.save(
            Store(
                name = "하루필름 강남2호점",
                type = StoreType.STORE,
                addressInfo = Address(address = "요기조기"),
                brand = brand,
                operatingTime = "24시간 영업",
                phoneNumber = "011-1234-5678",
                payTypes = listOf(PayType.CARD, PayType.SIMPLE)
            )
        )

        storeRepository.save(
            Store(
                name = "하루필름 잠실점",
                type = StoreType.STORE,
                addressInfo = Address(address = "요기조기"),
                brand = brand,
                operatingTime = "24시간 영업",
                phoneNumber = "011-1234-5678",
                payTypes = listOf(PayType.CARD, PayType.SIMPLE)
            )
        )

        // when & then
        mockMvc.perform(
            get("/stores?q=강남")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.content[0].store_id").value(store1.id))
            .andExpect(jsonPath("$.body.content[0].store_name").value("하루필름 강남점"))
            .andExpect(jsonPath("$.body.content[0].store_address").value("여기저기"))
            .andExpect(jsonPath("$.body.content[0].review_cnt").isNumber)
            .andExpect(jsonPath("$.body.content[1].store_id").value(store2.id))
            .andExpect(jsonPath("$.body.content[1].store_name").value("하루필름 강남2호점"))
            .andExpect(jsonPath("$.body.content[1].store_address").value("요기조기"))
            .andExpect(jsonPath("$.body.content[1].review_cnt").isNumber)
            .andDo(print())
    }

    @Test
    @DisplayName("매장의 브랜드 이미지 조회")
    fun get_store_brand_image() {
        // given
        val image = imageRepository.save(
            Image(
                uuid = UUID.randomUUID().toString(),
                originalName = "image.jpg",
                name = "image",
                extension = "jpg",
                path = "/images/123"
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
            get("/stores/{storeId}/brand-logo-image", store.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.brand_id").value(brand.id))
            .andExpect(jsonPath("$.body.brand_logo_image_id").value(image.id))
            .andExpect(jsonPath("$.body.image_url").value("https://www.forefilm.com/images/123"))
            .andDo(print())
    }

    @Test
    @DisplayName("매장 사진 리스트 조회")
    fun get_store_images() {
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
                addressInfo = Address(address = "여기저기"),
                brand = brand,
                operatingTime = "24시간 영업",
                phoneNumber = "014-1234-5678",
                payTypes = listOf(PayType.CARD, PayType.CASH)
            )
        )

        val reviewImages = mutableListOf<Image>()
        for (i in 0..9) {
            reviewImages.add(
                imageRepository.save(
                    Image(
                        uuid = UUID.randomUUID().toString(),
                        originalName = "image$i.jpg",
                        name = "image$i",
                        extension = "jpg",
                        path = "/image$i"
                    )
                )
            )
        }

        val review1 = reviewRepository.save(
            Review(
                store = store,
                contents = "내용",
                nickname = "계정",
                password = "1234",
                isDeleted = false
            )
        )
        val review2 = reviewRepository.save(
            Review(
                store = store,
                contents = "내용",
                nickname = "계정",
                password = "1234",
                isDeleted = false
            )
        )
        val review3 = reviewRepository.save(
            Review(
                store = store,
                contents = "내용",
                nickname = "계정",
                password = "1234",
                isDeleted = false
            )
        )


        for (reviewImage in reviewImages.subList(0, 3)) {
            reviewImageRepository.save(ReviewImage(review1, reviewImage))
        }

        for (reviewImage in reviewImages.subList(3, 8)) {
            reviewImageRepository.save(ReviewImage(review2, reviewImage))
        }

        for (reviewImage in reviewImages.subList(8, 10)) {
            reviewImageRepository.save(ReviewImage(review3, reviewImage))
        }


        // when & then - limit만 주어진 경우 (최신 사진 limit개 조회)
        val limit = 3
        mockMvc.perform(
            get("/stores/{storeId}/images?limit={limit}", store.id, limit)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images.size()").value(limit))
            .andExpect(jsonPath("$.body.images[0].image_id").value(reviewImages[9].id))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com/image9"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(reviewImages[8].id))
            .andExpect(jsonPath("$.body.images[1].image_url").value("https://www.forefilm.com/image8"))
            .andExpect(jsonPath("$.body.images[2].image_id").value(reviewImages[7].id))
            .andExpect(jsonPath("$.body.images[2].image_url").value("https://www.forefilm.com/image7"))
            .andExpect(jsonPath("$.body.last_image_id").value(reviewImages[7].id))
            .andDo(print())

        // when & then - limit, offset 둘다 주어진 경우
        var offset = reviewImages[7].id
        mockMvc.perform(
            get("/stores/{storeId}/images?limit={limit}&offset={offset}", store.id, limit, offset)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images.size()").value(limit))
            .andExpect(jsonPath("$.body.images[0].image_id").value(reviewImages[6].id))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com/image6"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(reviewImages[5].id))
            .andExpect(jsonPath("$.body.images[1].image_url").value("https://www.forefilm.com/image5"))
            .andExpect(jsonPath("$.body.images[2].image_id").value(reviewImages[4].id))
            .andExpect(jsonPath("$.body.images[2].image_url").value("https://www.forefilm.com/image4"))
            .andExpect(jsonPath("$.body.last_image_id").value(reviewImages[4].id))
            .andDo(print())

        // when & then - 사진이 더이상 없는 경우
        offset = reviewImages[0].id
        mockMvc.perform(
            get("/stores/{storeId}/images?limit={limit}&offset={offset}", store.id, limit, offset)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images").isEmpty)
            .andExpect(jsonPath("$.body.last_image_id").value(null))
            .andDo(print())
    }

}