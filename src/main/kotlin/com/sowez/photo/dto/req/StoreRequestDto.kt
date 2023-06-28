package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class StoreCreateReqDto(
    @field:NotBlank
    val storeName: String,
    @field:NotBlank
    val storeType: StoreType,
    @field:NotBlank
    val storeAddress: String,
    @field:NotNull
    val brandId: Long,
    val storeOperatingTime: String?,
    val storePhoneNum: String?,
    val payTypes: List<PayType>?
): SnakeCaseDto()

data class StoreEditReqDto(
    @field:NotBlank
    val storeName: String,
    @field:NotBlank
    val storeType: StoreType,
    @field:NotBlank
    val storeAddress: String,
    @field:NotNull
    val brandId: Long,
    val storeOperatingTime: String?,
    val storePhoneNum: String?,
    val payTypes: List<PayType>?
): SnakeCaseDto()
