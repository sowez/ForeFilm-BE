package com.sowez.photo.service

import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.req.StoreEditReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.entity.Address
import com.sowez.photo.entity.Store
import com.sowez.photo.error.BrandNotFoundException
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.repository.BrandRepository
import com.sowez.photo.repository.StoreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
interface StoreService {
    fun createStore(createDto: StoreCreateReqDto): Long
    fun editStoreInfo(storeId: Long, editDto: StoreEditReqDto)
    fun getStoreInfo(storeId: Long): StoreInfoResDto
    fun searchStore(query: String, pageable: Pageable): Page<StoreSearchResDto>
    fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto
    fun getStoreImages(storeId: Long, limit: Int, offset: Int?): StoreImagesResDto

}

@Service
@Transactional(readOnly = true)
class StoreServiceImpl(
    val storeRepository: StoreRepository,
    val brandRepository: BrandRepository
): StoreService {

    @Transactional
    override fun createStore(createDto: StoreCreateReqDto): Long {
        val brand = brandRepository.findById(createDto.brandId)
            .orElseThrow { BrandNotFoundException(createDto.brandId) }

        val store = storeRepository.save(
            Store(
                name = createDto.storeName,
                type = createDto.storeType,
                addressInfo = Address(address = createDto.storeAddress),
                brand = brand,
                operatingTime = createDto.storeOperatingTime,
                phoneNumber = createDto.storePhoneNum,
                payTypes = createDto.payTypes
            )
        )

        return store.id
    }

    @Transactional
    override fun editStoreInfo(storeId: Long, editDto: StoreEditReqDto) {
        storeRepository.findById(storeId)
            .orElseThrow{ StoreNotFoundException(storeId) }
            .apply {
                if (this.name != editDto.storeName) {
                    this.editName(editDto.storeName)
                }

                if (this.type != editDto.storeType) {
                    this.editType(editDto.storeType)
                }

                if (this.addressInfo.address != editDto.storeAddress) {
                    this.editAddressInfo(Address(address=editDto.storeAddress))
                }

                if (this.brand.id != editDto.brandId) {
                    val brand = brandRepository.findById(editDto.brandId)
                        .orElseThrow { BrandNotFoundException(editDto.brandId) }
                    this.editBrand(brand)
                }

                if (this.operatingTime != editDto.storeOperatingTime) {
                    this.editOperatingTime(editDto.storeOperatingTime)
                }

                if (this.phoneNumber != editDto.storePhoneNum) {
                    this.editPhoneNumber(editDto.storePhoneNum)
                }

                if (this.getPayTypes() != editDto.payTypes) {
                    this.editPayTypes(editDto.payTypes)
                }
            }
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