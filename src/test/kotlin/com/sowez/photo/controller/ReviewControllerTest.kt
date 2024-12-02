package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.dto.req.ReviewCreateReqDto
import com.sowez.photo.entity.*
import com.sowez.photo.repository.*
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val storeRepository: StoreRepository,
    @Autowired val brandRepository: BrandRepository,
    @Autowired val imageRepository: ImageRepository,
    @Autowired val reviewRepository: ReviewRepository,
    @Autowired val reviewImageRepository: ReviewImageRepository,
    @Autowired val reviewTagRepository: ReviewTagRepository,
    @Autowired val tagRepository: TagRepository,

    ){
    @Test
    @DisplayName("새로운 리뷰 작성")
    fun create_new_review(){
        //given
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

        val tag1 = tagRepository.save(
            Tag(
                contents = "깔끔해요",
                emojiName = "clean"
            )
        )
        val tag2 = tagRepository.save(
            Tag(
                contents = "핫해요",
                emojiName = "hot"
            )
        )

        val image1 = imageRepository.save(
            Image(
                uuid = "uuid1",
                originalName = "originalName1",
                name = "name1",
                extension = "jpg",
                path = "path1"
            )
        )
        val image2 = imageRepository.save(
            Image(
                uuid = "uuid1",
                originalName = "originalName1",
                name = "name1",
                extension = "jpg",
                path = "path1"
            )
        )

        val review = ReviewCreateReqDto(
            storeId = store.id,
            reviewNickname = "eumji",
            reviewPassword = "123",
            reviewContents = "123",
            reviewTagIds = listOf(tag1.id, tag2.id),
            reviewImages = listOf(image1.originalName, image2.originalName)
        )

        // when & then
        mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(review))
        )
            .andExpect(status().isOk)
            .andDo(print())
    }

    @Test
    @DisplayName("리뷰 한개 조회")
    fun get_single_review(){
        //given
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

        val tag1 = tagRepository.save(
            Tag(
                contents = "깔끔해요",
                emojiName = "clean"
            )
        )
        val tag2 = tagRepository.save(
            Tag(
                contents = "핫해요",
                emojiName = "hot"
            )
        )

        val image1 = imageRepository.save(
            Image(
                uuid = "uuid1",
                originalName = "originalName1",
                name = "name1",
                extension = "jpg",
                path = "path1"
            )
        )
        val image2 = imageRepository.save(
            Image(
                uuid = "uuid1",
                originalName = "originalName1",
                name = "name1",
                extension = "jpg",
                path = "path1"
            )
        )

        val review = reviewRepository.save(
            Review(
                store = store,
                nickname = "eumji",
                password = "123",
                contents = "123",
                isDeleted = false,
            )
        )

        val reviewTag1 = reviewTagRepository.save(
            ReviewTag(
                review = review,
                tag = tag1
            )
        )
        val reviewTag2 = reviewTagRepository.save(
            ReviewTag(
                review = review,
                tag = tag2
            )
        )

        val reviewImage1 = reviewImageRepository.save(
            ReviewImage(
                review = review,
                image = image1
            )
        )
        val reviewImage2 = reviewImageRepository.save(
            ReviewImage(
                review = review,
                image = image2
            )
        )

        // when & then
        mockMvc.perform(
            get("/reviews/{review_id}", review.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.review_nickname").value(review.nickname))
            .andExpect(jsonPath("$.body.review_contents").value(review.contents))
            .andExpect(jsonPath("$.body.review_tags[0].tag_id").value(tag1.id))
            .andExpect(jsonPath("$.body.review_tags[0].tag_contents").value(tag1.contents))
            .andExpect(jsonPath("$.body.review_tags[0].tag_emoji_name").value(tag1.emojiName))
            .andExpect(jsonPath("$.body.review_tags[1].tag_id").value(tag2.id))
            .andExpect(jsonPath("$.body.review_tags[1].tag_contents").value(tag2.contents))
            .andExpect(jsonPath("$.body.review_tags[1].tag_emoji_name").value(tag2.emojiName))
            .andExpect(jsonPath("$.body.review_images[0].image_id").value(image1.id))
            .andExpect(jsonPath("$.body.review_images[1].image_id").value(image2.id))
            .andDo(print())
    }

    @Test
    @DisplayName("리뷰 이미지 조회")
    fun get_review_images(){
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
                isDeleted = false,
            )
        )
        val review2 = reviewRepository.save(
            Review(
                store = store,
                nickname = "계정",
                password = "1234",
                contents = "내용",
                isDeleted = false,
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

        // when & then
        mockMvc.perform(
            get("/stores/{store_id}/review-images", 1L)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images[0].image_id").value(1))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com/images/1"))
            .andExpect(jsonPath("$.body.images[1].image_id").value(2))
            .andExpect(jsonPath("$.body.images[1].image_url").value("https://www.forefilm.com/images/2"))
            .andDo(print())
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
            .andExpect(jsonPath("$.body.reviews[0].created_datetime").value(LocalDateTime.of(2023,8,12,13,35, 1).toString()))
            .andExpect(jsonPath("$.body.reviews[0].contents").value("content"))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_id").value(1))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_contents").value("1"))
            .andExpect(jsonPath("$.body.reviews[0].tags[0].tag_emoji_name").value("1"))
            .andExpect(jsonPath("$.body.reviews[0].thumbnail_image_url").value("url"))
            .andExpect(jsonPath("$.body.reviews[0].image_count").value(1))
            .andExpect(jsonPath("$.body.last_review_id").value(10))
            .andDo(print())
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
            .andDo(print())
    }
}