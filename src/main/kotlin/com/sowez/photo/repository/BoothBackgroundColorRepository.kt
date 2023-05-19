package com.sowez.photo.repository

import com.sowez.photo.entity.BoothBackgroundColor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoothBackgroundColorRepository: JpaRepository<BoothBackgroundColor, Long> {
}