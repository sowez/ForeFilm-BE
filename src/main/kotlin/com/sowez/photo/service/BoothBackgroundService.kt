package com.sowez.photo.service

import com.sowez.photo.dto.*
import com.sowez.photo.repository.BoothBackgroundColorRepository
import org.springframework.stereotype.Service

@Service
interface BoothBackgroundService {
    fun getBoothBackgroundInfo(): BoothBackgroundInfosResDto
}

@Service
class BoothBackgroundServiceImpl(
        val boothBackgroundColorRepository: BoothBackgroundColorRepository
) : BoothBackgroundService {
    override fun getBoothBackgroundInfo(): BoothBackgroundInfosResDto {
        val boothBackgroundColors = boothBackgroundColorRepository.findAll()

        return BoothBackgroundInfosResDto(boothBackgroundColors.map { boothBackgroundColor ->
            BoothBackgroundInfoResDto(
                    boothBackgroundColorId = boothBackgroundColor.id,
                    boothBackgroundColorName = boothBackgroundColor.name,
                    boothBackgroundColorCode = boothBackgroundColor.code
            )
        })
    }
}