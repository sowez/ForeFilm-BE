package com.sowez.photo.service

import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.req.StoreEditReqDto
import com.sowez.photo.dto.res.*
import com.sowez.photo.entity.Address
import com.sowez.photo.entity.Image
import com.sowez.photo.entity.Store
import com.sowez.photo.error.BrandNotFoundException
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.repository.*
import org.springframework.data.domain.Page
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
    val brandRepository: BrandRepository,
    val reviewRepository: ReviewRepository,
    val reviewImageRepository: ReviewImageRepository,
    val imageRepository: ImageRepository
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
        return storeRepository.findByNameContaining(query, pageable)
            .map { store ->
                StoreSearchResDto(
                    storeId = store.id,
                    storeName = store.name,
                    storeAddress = store.addressInfo.address,
                    reviewCnt = store.getReviewCount()
                )
            }
    }

    override fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto {
        val store = storeRepository.findById(storeId)
            .orElseThrow { StoreNotFoundException(storeId) }
        val brand = store.brand
        val logoImage = brand.logoImage

        return StoreBrandLogoImageResDto(
                brandId = brand.id,
                brandLogoImageId = logoImage.id,
                imageUrl = getImageUrl(logoImage)
        )
    }

    override fun getStoreImages(storeId: Long, limit: Int, offset: Int?): StoreImagesResDto {
        val imageIds = if (offset != null){
            reviewImageRepository.findNextImageIds(storeId, offset, PageRequest.of(0, limit))
        } else {
            reviewImageRepository.findNewestImageIds(storeId, PageRequest.of(0, limit))
        }

        val imageResDtos = imageRepository.findAllByIdInOrderByIdDesc(imageIds)
            .map { image -> StoreImageResDto(imageId = image.id, imageUrl = getImageUrl(image)) }
        val lastId = if(imageResDtos.isNotEmpty()) imageResDtos.last().imageId else null

        return StoreImagesResDto(imageResDtos, lastId)
    }

    private fun getImageUrl(image: Image): String {
        return "https://www.forefilm.com" + image.path
    }

}