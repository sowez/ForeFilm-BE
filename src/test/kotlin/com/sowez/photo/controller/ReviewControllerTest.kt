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

        reviewTagRepository.save(
            ReviewTag(
                review = review,
                tag = tag1
            )
        )
        reviewTagRepository.save(
            ReviewTag(
                review = review,
                tag = tag2
            )
        )

        reviewImageRepository.save(
            ReviewImage(
                review = review,
                image = image1
            )
        )
        reviewImageRepository.save(
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
    @DisplayName("스토어 리뷰 조회")
    fun get_reviews(){
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

        val review1 = reviewRepository.save(
            Review(
                store = store,
                nickname = "eumji",
                password = "123",
                contents = "review1",
                isDeleted = false,
            )
        )
        val review2 = reviewRepository.save(
            Review(
                store = store,
                nickname = "eumji",
                password = "123",
                contents = "review2",
                isDeleted = false,
            )
        )

        reviewTagRepository.save(
            ReviewTag(
                review = review1,
                tag = tag1
            )
        )
        reviewTagRepository.save(
            ReviewTag(
                review = review2,
                tag = tag2
            )
        )

        reviewImageRepository.save(
            ReviewImage(
                review = review1,
                image = image1
            )
        )
        reviewImageRepository.save(
            ReviewImage(
                review = review2,
                image = image2
            )
        )

        // when & then
        mockMvc.perform(
            get("/reviews/{storeId}/reviews", store.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.reviews[1].review_id").value(review1.id))
            .andExpect(jsonPath("$.body.reviews[1].review_nickname").value(review1.nickname))
            .andExpect(jsonPath("$.body.reviews[1].review_contents").value(review1.contents))
            .andExpect(jsonPath("$.body.reviews[1].review_tags[0].tag_id").value(tag1.id))
            .andExpect(jsonPath("$.body.reviews[1].review_tags[0].tag_contents").value(tag1.contents))
            .andExpect(jsonPath("$.body.reviews[1].review_tags[0].tag_emoji_name").value(tag1.emojiName))
            .andExpect(jsonPath("$.body.reviews[1].thumbnail_image_url").value("https://www.forefilm.com"+image1.path))
            .andExpect(jsonPath("$.body.reviews[1].image_count").value(1))
            .andExpect(jsonPath("$.body.reviews[0].review_id").value(review2.id))
            .andExpect(jsonPath("$.body.reviews[0].review_nickname").value(review2.nickname))
            .andExpect(jsonPath("$.body.reviews[0].review_contents").value(review2.contents))
            .andExpect(jsonPath("$.body.reviews[0].review_tags[0].tag_id").value(tag2.id))
            .andExpect(jsonPath("$.body.reviews[0].review_tags[0].tag_contents").value(tag2.contents))
            .andExpect(jsonPath("$.body.reviews[0].review_tags[0].tag_emoji_name").value(tag2.emojiName))
            .andExpect(jsonPath("$.body.reviews[0].thumbnail_image_url").value("https://www.forefilm.com"+image2.path))
            .andExpect(jsonPath("$.body.reviews[0].image_count").value(1))
            .andExpect(jsonPath("$.body.last_review_id").value(1))
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
                        originalName = "original_image$i",
                        name = "name_image$i",
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
            get("/reviews/{store_id}/images", store.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.images[0].image_id").value(reviewImages[9].id))
            .andExpect(jsonPath("$.body.images[0].image_url").value("https://www.forefilm.com"+reviewImages[9].path))
            .andExpect(jsonPath("$.body.images[9].image_id").value(reviewImages[0].id))
            .andExpect(jsonPath("$.body.images[9].image_url").value("https://www.forefilm.com"+reviewImages[0].path))
            .andDo(print())
    }

    @Test
    @DisplayName("스토어 태그 조회")
    fun get_review_tags(){
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

        val review1 = reviewRepository.save(
            Review(
                store = store,
                nickname = "eumji",
                password = "123",
                contents = "review1",
                isDeleted = false,
            )
        )
        val review2 = reviewRepository.save(
            Review(
                store = store,
                nickname = "eumji",
                password = "123",
                contents = "review2",
                isDeleted = false,
            )
        )

        reviewTagRepository.save(
            ReviewTag(
                review = review1,
                tag = tag1
            )
        )
        reviewTagRepository.save(
            ReviewTag(
                review = review1,
                tag = tag2
            )
        )
        reviewTagRepository.save(
            ReviewTag(
                review = review2,
                tag = tag2
            )
        )

        // when && then
        mockMvc.perform(
            get("/reviews/{storeId}/tags",store.id)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.body.total_cnt").value(3))
            .andExpect(jsonPath("$.body.tags[0].tag_id").value(tag1.id))
            .andExpect(jsonPath("$.body.tags[0].tag_contents").value(tag1.contents))
            .andExpect(jsonPath("$.body.tags[0].tag_emoji_name").value(tag1.emojiName))
            .andExpect(jsonPath("$.body.tags[0].tag_count").value(1))
            .andExpect(jsonPath("$.body.tags[1].tag_id").value(tag2.id))
            .andExpect(jsonPath("$.body.tags[1].tag_contents").value(tag2.contents))
            .andExpect(jsonPath("$.body.tags[1].tag_emoji_name").value(tag2.emojiName))
            .andExpect(jsonPath("$.body.tags[1].tag_count").value(2))
            .andDo(print())
    }
}