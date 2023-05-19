package com.sowez.photo.repository

import com.sowez.photo.entity.Layout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LayoutRepository: JpaRepository<Layout, Long> {
}