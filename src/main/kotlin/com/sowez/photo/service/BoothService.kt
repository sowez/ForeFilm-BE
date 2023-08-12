package com.sowez.photo.service

import com.sowez.photo.dto.BoothInfoResDto
import com.sowez.photo.dto.req.BoothCreateReqDto
import com.sowez.photo.dto.req.BoothEditReqDto
import com.sowez.photo.type.DownloadType
import org.springframework.stereotype.Service

@Service
interface BoothService {
    fun createBooth(createDto: BoothCreateReqDto): Long
    fun editBoothInfo(storeId: Long, editDto: BoothEditReqDto)
    fun getBoothInfo(storeId: Long): BoothInfoResDto
}

@Service
class BoothServiceTestImpl : BoothService {
    override fun createBooth(createDto: BoothCreateReqDto): Long {
        println("BoothServiceTestImpl.createBooth")
        return 1
    }

    override fun editBoothInfo(storeId: Long, editDto: BoothEditReqDto) {
        println("BoothServiceTestImpl.editBoothInfo")
        return
    }

    override fun getBoothInfo(storeId: Long): BoothInfoResDto {
        println("BoothServiceTestImpl.getBoothInfo")
        return BoothInfoResDto(
                boothCount = 3,
                boothBackgroundColorName = listOf("분홍", "파랑", "회색"),
                layoutImageUrls = listOf("forefilm.com"),
                minPeopleCount = 1,
                maxPeopleCount = 6,
                downloadTypes = listOf(DownloadType.QR, DownloadType.APP),
                downloadPeriod = "7",
                isReshoot = true,
                isRemote = true,
                isCurlingIron = true,
                isEnvelope = true,
                isFootrest = true
        )
    }


}