package com.sowez.photo.service

import com.sowez.photo.dto.BoothBackgroundInfoResDto
import com.sowez.photo.dto.BoothBackgroundsResDto
import com.sowez.photo.dto.BrandInfoResDto
import com.sowez.photo.dto.BrandsResponseDto
import org.springframework.stereotype.Service

@Service
interface BoothBackgroundService {
    fun getBoothBackgroundInfo(): BoothBackgroundsResDto
}

@Service
class BoothBackgroundServiceTestImpl : BoothBackgroundService {
    override fun getBoothBackgroundInfo(): BoothBackgroundsResDto {
        return BoothBackgroundsResDto(
                listOf(
                        BoothBackgroundInfoResDto(boothBackgroundColorId = 1, boothBackgroundColorName = "흰색", boothBackgroundColorCode ="#FFFFFF"),
                        BoothBackgroundInfoResDto(boothBackgroundColorId = 2, boothBackgroundColorName = "하늘색", boothBackgroundColorCode ="#A1B1C1")
                )
        )
    }

}