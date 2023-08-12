package com.sowez.photo.service

import com.sowez.photo.dto.BoothBackgroundInfoResDto
import org.springframework.stereotype.Service

@Service
interface BoothBackgroundService {
    fun getBoothBackgroundInfo(storeId: Long): BoothBackgroundInfoResDto
}