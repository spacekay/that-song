package com.spacekay.thatsong.domain.albums.repository

import com.spacekay.thatsong.domain.albums.entity.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumRepository : JpaRepository<Album, Long> {
    fun findAllByIsDeletedFalseOrderByReleaseDate(): List<Album>

    fun findAllByArtistIdAndIsDeletedFalseOrderByReleaseDate(artistId: Long): List<Album>
}
