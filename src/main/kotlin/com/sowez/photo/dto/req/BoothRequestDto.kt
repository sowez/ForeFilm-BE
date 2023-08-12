package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto

data class BoothCreateReqDto(
        val boothCount: Int,
        val boothBackgroundColorName: List<String>,
        val layoutImagesIds: List<Int>,
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadType: List<String>,
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
        val layoutImagesIds: List<Int>,
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadType: List<String>,
        val downloadPeriod: String,
        val isReshoot: Boolean,
        val isRemote: Boolean,
        val isCurlingIron: Boolean,
        val isEnvelope: Boolean,
        val isFootrest: Boolean
) : SnakeCaseDto()
