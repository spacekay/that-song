package com.spacekay.thatsong.domain.artists.repository

import com.spacekay.thatsong.domain.artists.entity.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtistRepository : JpaRepository<Artist, Long> {
    fun findAllByIsDeletedFalse(): List<Artist>
}
