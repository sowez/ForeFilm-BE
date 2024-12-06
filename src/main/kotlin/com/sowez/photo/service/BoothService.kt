package com.sowez.photo.service

import com.sowez.photo.dto.BoothInfoResDto
import com.sowez.photo.dto.req.BoothCreateReqDto
import com.sowez.photo.dto.req.BoothEditReqDto
import com.sowez.photo.entity.*
import com.sowez.photo.error.StoreNotFoundException
import com.sowez.photo.repository.*
import com.sowez.photo.type.DownloadType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
interface BoothService {
    fun createBooth(storeId: Long, createDto: BoothCreateReqDto)
    fun editBoothInfo(storeId: Long, editDto: BoothEditReqDto)
    fun getBoothInfo(storeId: Long): BoothInfoResDto
}

@Service
class BoothServiceImpl(
        val storeRepository: StoreRepository,
        val storeBoothBackgroundColorRepository: StoreBoothBackgroundColorRepository,
        val boothBackgroundColorRepository: BoothBackgroundColorRepository,
) : BoothService {
    override fun createBooth(storeId: Long, createDto: BoothCreateReqDto) {
        println("BoothServiceTestImpl.createBooth")

        // STORE 부스정보 등록
        val store = storeRepository.findById(storeId)
                .orElseThrow { StoreNotFoundException(storeId) }

        store.addBoothInfo(
                boothInfo = createDto.toEntity()
        )

        storeRepository.save(store)

        // STORE_BOOTH_BACKGROUND_COLOR 등록
        createDto.boothBackgroundColorIds?.let { ids ->
            val backgroundColors = boothBackgroundColorRepository.findAllById(ids)

            backgroundColors.map { color ->
                StoreBoothBackgroundColor(
                        store = store,
                        boothBackgroundColor = color
                )
            }.let { storeBoothBackgroundColors ->
                storeBoothBackgroundColorRepository.saveAll(storeBoothBackgroundColors)
            }
        }
    }

    @Transactional
    override fun editBoothInfo(storeId: Long, editDto: BoothEditReqDto) {
        println("BoothServiceTestImpl.editBoothInfo")

        // STORE 부스정보 업데이트
        val store = storeRepository.findById(storeId)
                .orElseThrow { StoreNotFoundException(storeId) }

        store.addBoothInfo(
                boothInfo = editDto.toEntity()
        )

        storeRepository.save(store)

        // boothBackgroundColor 삭제 후 재생성
        storeBoothBackgroundColorRepository.deleteByStore(store)

        editDto.boothBackgroundColorIds?.let { ids ->
            val backgroundColors = boothBackgroundColorRepository.findAllById(ids)

            backgroundColors.map { color ->
                StoreBoothBackgroundColor(
                        store = store,
                        boothBackgroundColor = color
                )
            }.let { storeBoothBackgroundColors ->
                storeBoothBackgroundColorRepository.saveAll(storeBoothBackgroundColors)
            }
        }

        return
    }

    override fun getBoothInfo(storeId: Long): BoothInfoResDto {
        println("BoothServiceTestImpl.getBoothInfo")

        val store = storeRepository.findById(storeId)
                .orElseThrow { StoreNotFoundException(storeId) }

        // StoreBoothBackgroundColors 조회
        val storeBoothBackgroundColors = storeBoothBackgroundColorRepository.findByStore(store)

        return store
                .run {
                    BoothInfoResDto(
                            boothCount = this.boothInfo?.boothCount,
                            boothBackgroundColorIds = storeBoothBackgroundColors?.map { it.boothBackgroundColor.id },
                            boothBackgroundColorNames = storeBoothBackgroundColors?.map { it.boothBackgroundColor.name },
                            boothBackgroundColorCodes = storeBoothBackgroundColors?.map { it.boothBackgroundColor.code },
                            minPeopleCount = this.boothInfo?.minPeopleCount,
                            maxPeopleCount = this.boothInfo?.maxPeopleCount,
                            downloadTypes = this.boothInfo?.getDownloadTypes(),
                            downloadPeriod = this.boothInfo?.downloadPeriod,
                            isReshoot = this.boothInfo?.isReshoot,
                            isRemote = this.boothInfo?.isRemote,
                            isCurlingIron = this.boothInfo?.isCurlingIron,
                            isEnvelope = this.boothInfo?.isEnvelope,
                            isFootrest = this.boothInfo?.isFootrest
                    )
                }
    }


}