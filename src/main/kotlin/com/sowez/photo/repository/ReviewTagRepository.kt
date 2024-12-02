package com.sowez.photo.repository

import com.sowez.photo.entity.ReviewTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewTagRepository: JpaRepository<ReviewTag, Long> {
    @Query(value = "select rt.tag.id " +
        "from ReviewTag rt join rt.review r " +
        "where r.id = :reviewId")
    fun findTagIdsWithReviewId(reviewId: Long): List<Long>

    @Query(value = "select rt.tag.id " +
        "from ReviewTag rt join rt.review r " +
        "where r.store.id = :storeId " +
        "order by rt.tag.id asc")
    fun findTagIdsWithStoreId(storeId: Long): List<Long>

}