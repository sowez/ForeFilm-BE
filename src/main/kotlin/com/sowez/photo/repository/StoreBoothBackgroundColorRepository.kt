package com.sowez.photo.repository

import com.sowez.photo.entity.Store
import com.sowez.photo.entity.StoreBoothBackgroundColor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreBoothBackgroundColorRepository: JpaRepository<StoreBoothBackgroundColor, Long> {
    fun findByStore(store: Store): List<StoreBoothBackgroundColor>?
    fun deleteByStore(store: Store): List<StoreBoothBackgroundColor>?
}