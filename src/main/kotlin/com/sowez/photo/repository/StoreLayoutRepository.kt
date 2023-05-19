package com.sowez.photo.repository

import com.sowez.photo.entity.StoreLayout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreLayoutRepository: JpaRepository<StoreLayout, Long> {
}