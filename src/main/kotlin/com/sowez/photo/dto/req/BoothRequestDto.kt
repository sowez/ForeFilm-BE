package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.type.DownloadType

data class BoothCreateReqDto(
        val boothCount: Int,
        val boothBackgroundColorName: List<String>,
        val layoutImagesIds: List<Long>,
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadTypes: List<DownloadType>,
        val downloadPeriod: String,
        val isReshoot: Boolean,
        val isRemote: Boolean,
        val isCurlingIron: Boolean,
        val isEnvelope: Boolean,
        val isFootrest: Boolean
) : SnakeCaseDto()

data class BoothEditReqDto(
        val boothCount: Int,
        val boothBackgroundColorName: List<String>,
        val layoutImagesIds: List<Long>,
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadTypes: List<DownloadType>,
        val downloadPeriod: String,
        val isReshoot: Boolean,
        val isRemote: Boolean,
        val isCurlingIron: Boolean,
        val isEnvelope: Boolean,
        val isFootrest: Boolean
) : SnakeCaseDto()
