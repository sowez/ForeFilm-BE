package com.sowez.photo.service

import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.repository.StoreRepository
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
interface StoreService {
    fun createStore(createDto: StoreCreateReqDto): Long
    fun editStoreInfo(storeId: Long)
    fun getStoreInfo(storeId: Long): StoreInfoResDto
    fun searchStore(query: String, pageable: Pageable): Page<StoreSearchResDto>
    fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto
    fun getStoreImages(storeId: Long, limit: Int, offset: Int?): StoreImagesResDto

}

@Service
@Transactional(readOnly = true)
class StoreServiceImpl(
    val storeRepository: StoreRepository
): StoreService {
    override fun createStore(createDto: StoreCreateReqDto): Long {
        println("StoreServiceTestImpl.createStore")
        return 1L
    }

    override fun editStoreInfo(storeId: Long) {
        println("StoreServiceTestImpl.editStoreInfo")
    }

    override fun getStoreInfo(storeId: Long): StoreInfoResDto {
        return storeRepository.findById(storeId)
            .orElseThrow{ StoreNotFoundException(storeId) }
            .run {
                StoreInfoResDto(
                    storeName = this.name,
                    storeType = this.type,
                    storeAddress = this.addressInfo.address,
                    brandId = this.brand.id,
                    storeOperatingTime = this.operatingTime,
                    storePhoneNum = this.phoneNumber,
                    payTypes = this.getPayTypes().stream().toList()
                )
            }
    }

    override fun searchStore(query: String, pageable: Pageable): Page<StoreSearchResDto> {
        println("StoreServiceTestImpl.searchStore")
        return PageImpl(listOf(
                StoreSearchResDto(storeId = 1L, storeName = "하루필름 강남점", storeAddress = "여기저기", reviewCnt = 101),
                StoreSearchResDto(storeId = 4L, storeName = "하루필름 강남2호점", storeAddress = "요기조기", reviewCnt = 79),
        ), PageRequest.of(1, 10), 2)
    }

    override fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto {
        println("StoreServiceTestImpl.getBrandLogoImage")
        return StoreBrandLogoImageResDto(
                brandId = 10L,
                brandLogoImageId = 100L,
                imageUrl = "https://www.forefilm.com/images/123"
        )
    }

    override fun getStoreImages(storeId: Long, limit: Int, offset: Int?): StoreImagesResDto {
        println("StoreServiceTestImpl.getStoreImages(storeId=$storeId, limit=$limit, offset=$offset)")
        return StoreImagesResDto(
                images = listOf(
                        StoreImageResDto(imageId = 1L, imageUrl = "https://www.forefilm.com/images/1234"),
                        StoreImageResDto(imageId = 5L, imageUrl = "https://www.forefilm.com/images/4321"),
                        StoreImageResDto(imageId = 10L, imageUrl = "https://www.forefilm.com/images/43")
                ),
                lastImageId = 10L
        )
    }

}