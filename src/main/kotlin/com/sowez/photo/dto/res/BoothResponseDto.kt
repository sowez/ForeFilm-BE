package com.sowez.photo.dto

import com.sowez.photo.type.DownloadType

data class BoothInfoResDto (
        val boothCount: Int,
        val boothBackgroundColorName: List<String> = mutableListOf(),
        val layoutImageUrls: List<String> = mutableListOf(),
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadTypes: List<DownloadType> = mutableListOf(),
        val downloadPeriod: String,
        val isReshoot: Boolean,
        val isRemote: Boolean,
        val isCurlingIron: Boolean,
        val isEnvelope: Boolean,
        val isFootrest: Boolean,
): SnakeCaseDto()