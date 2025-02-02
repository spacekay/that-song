package com.spacekay.thatsong.domain.songs.repository

import com.spacekay.thatsong.domain.songs.entity.Song
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : JpaRepository<Song, Long> {
    fun findAllByIsDeletedFalse(): List<Song>
}