package com.sowez.photo.service

import com.sowez.photo.dto.req.StoreCreateReqDto
import com.sowez.photo.dto.res.StoreBrandLogoImageResDto
import com.sowez.photo.dto.res.StoreImagesResDto
import com.sowez.photo.dto.res.StoreInfoResDto
import com.sowez.photo.dto.res.StoreSearchResDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface StoreService {
    fun createStore(createDto: StoreCreateReqDto): Long
    fun editStoreInfo(storeId: Long)
    fun getStoreInfo(storeId: Long): StoreInfoResDto
    fun searchStore(query: String, pageable: Pageable): Page<StoreSearchResDto>
    fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto
    fun getStoreImages(storeId: Long, limit: Int, offset: Int): StoreImagesResDto

}

@Service
class StoreServiceTestImpl: StoreService {
    override fun createStore(createDto: StoreCreateReqDto): Long {
        TODO("Not yet implemented")
    }

    override fun editStoreInfo(storeId: Long) {
        TODO("Not yet implemented")
    }

    override fun getStoreInfo(storeId: Long): StoreInfoResDto {
        TODO("Not yet implemented")
    }

    override fun searchStore(query: String, pageable: Pageable): Page<StoreSearchResDto> {
        TODO("Not yet implemented")
    }

    override fun getBrandLogoImage(storeId: Long): StoreBrandLogoImageResDto {
        TODO("Not yet implemented")
    }

    override fun getStoreImages(storeId: Long, limit: Int, offset: Int): StoreImagesResDto {
        TODO("Not yet implemented")
    }
}

