package com.sowez.photo.repository

import com.sowez.photo.entity.Review
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    fun findAllByIdInOrderByIdDesc(ids: List<Long>): List<Review>

    @Query(value = "select r.id " +
        "from Review r " +
        "where r.store.id = :storeId and r.id < :lastId " +
        "order by r.id desc")
    fun findNextReviewIds(storeId: Long, lastId: Int, pageable: Pageable): List<Long>

    @Query(value = "select r.id " +
        "from Review r " +
        "where r.store.id = :storeId " +
        "order by r.id desc")
    fun findNewestReviewIds(storeId: Long, pageable: Pageable): List<Long>
}