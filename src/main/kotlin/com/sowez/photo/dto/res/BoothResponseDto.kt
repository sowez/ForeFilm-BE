package com.sowez.photo.dto

data class BoothInfoResDto (
        val boothCount: Int,
        val boothBackgroundColorName: List<String> = mutableListOf(),
        val layoutImagesIds: List<Int> = mutableListOf(),
        val minPeopleCount: Int,
        val maxPeopleCount: Int,
        val downloadType: List<String> = mutableListOf(),
        val downloadPeriod: String,
        val isReshoot: Boolean,
        val isRemote: Boolean,
        val isCurlingIron: Boolean,
        val isEnvelope: Boolean,
        val isFootrest: Boolean,
): SnakeCaseDto()