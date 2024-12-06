package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.entity.BoothInfo
import com.sowez.photo.type.DownloadType

data class BoothCreateReqDto(
        val boothCount: Int,
        val boothBackgroundColorIds: List<Long>?,
        val minPeopleCount: Int?,
        val maxPeopleCount: Int?,
        val downloadTypes: List<DownloadType>?,
        val downloadPeriod: String?,
        val isReshoot: Boolean?,
        val isRemote: Boolean?,
        val isCurlingIron: Boolean?,
        val isEnvelope: Boolean?,
        val isFootrest: Boolean?
) : SnakeCaseDto() {
    fun toEntity(): BoothInfo {
        return BoothInfo(
                boothCount = boothCount,
                minPeopleCount = minPeopleCount,
                maxPeopleCount = maxPeopleCount,
                downloadTypes = downloadTypes?.joinToString(","), // List를 String으로 변환
                downloadPeriod = downloadPeriod,
                isReshoot = isReshoot,
                isRemote = isRemote,
                isCurlingIron = isCurlingIron,
                isEnvelope = isEnvelope,
                isFootrest = isFootrest
        )
    }
}

data class BoothEditReqDto(
        val boothCount: Int?,
        val boothBackgroundColorIds: List<Long>?,
        val minPeopleCount: Int?,
        val maxPeopleCount: Int?,
        val downloadTypes: List<DownloadType>?,
        val downloadPeriod: String?,
        val isReshoot: Boolean?,
        val isRemote: Boolean?,
        val isCurlingIron: Boolean?,
        val isEnvelope: Boolean?,
        val isFootrest: Boolean?
) : SnakeCaseDto() {
    fun toEntity(): BoothInfo {
        return BoothInfo(
                boothCount = boothCount,
                minPeopleCount = minPeopleCount,
                maxPeopleCount = maxPeopleCount,
                downloadTypes = downloadTypes?.joinToString(","), // List를 String으로 변환
                downloadPeriod = downloadPeriod,
                isReshoot = isReshoot,
                isRemote = isRemote,
                isCurlingIron = isCurlingIron,
                isEnvelope = isEnvelope,
                isFootrest = isFootrest
        )
    }
}
