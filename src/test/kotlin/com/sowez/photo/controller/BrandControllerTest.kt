package com.sowez.photo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sowez.photo.entity.Brand
import com.sowez.photo.entity.Image
import com.sowez.photo.repository.BrandRepository
import com.sowez.photo.repository.ImageRepository
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
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
internal class BrandControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @Autowired val imageRepository: ImageRepository,
        @Autowired val brandRepository: BrandRepository
) {

    @Test
    fun getBrandInfo() {

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

        val image2 = imageRepository.save(
                Image(
                        uuid = UUID.randomUUID().toString(),
                        originalName = "image.jpg",
                        name = "image",
                        extension = "jpg",
                        path = "/image"
                )
        )

        val brand2 = brandRepository.save(
                Brand(
                        logoImage = image2,
                        name = "인생네컷"
                )
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[0].brand_id").value(brand.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[0].brand_name").value("하루필름"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[1].brand_id").value(brand2.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.brands[1].brand_name").value("인생네컷"))
                .andDo(MockMvcResultHandlers.print())

    }
}