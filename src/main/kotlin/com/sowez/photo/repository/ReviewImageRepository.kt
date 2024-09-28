package com.sowez.photo.repository

import com.sowez.photo.entity.Image
import com.sowez.photo.entity.Review
import com.sowez.photo.entity.ReviewImage
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewImageRepository: JpaRepository<ReviewImage, Long> {

    @Query(value = "select ri.image.id " +
            "from ReviewImage ri join ri.review r " +
            "where r.store.id = :storeId and ri.image.id < :lastId " +
            "order by ri.id desc")
    fun findNextImageIds(storeId: Long, lastId: Int, pageable: Pageable): List<Long>

    @Query(value = "select ri.image.id " +
            "from ReviewImage ri join ri.review r " +
            "where r.store.id = :storeId " +
            "order by ri.id desc")
    fun findNewestImageIds(storeId: Long, pageable: Pageable): List<Long>
}