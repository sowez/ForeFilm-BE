package com.sowez.photo.service

import com.sowez.photo.dto.BoothInfoResDto
import com.sowez.photo.dto.req.BoothCreateReqDto
import org.springframework.stereotype.Service

@Service
interface BoothService {
    fun createBooth(createDto: BoothCreateReqDto): Long
    fun editBoothInfo(storeId: Long)
    fun getBoothInfo(storeId: Long): BoothInfoResDto
}