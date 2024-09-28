package com.sowez.photo.repository

import com.sowez.photo.entity.Store
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository: JpaRepository<Store, Long> {
    fun findByNameContaining(name: String, pageable: Pageable): Page<Store>
}