package com.spacekay.thatsong.domain.albums.service

import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumUpdateRequestDto
import com.spacekay.thatsong.domain.albums.entity.Album
import com.spacekay.thatsong.domain.albums.entity.AlbumType
import com.spacekay.thatsong.domain.albums.repository.AlbumRepository
import com.spacekay.thatsong.domain.artists.repository.ArtistRepository
import com.spacekay.thatsong.exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AlbumService(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {

    /**
     * . 앨범 목록 조회
     */
    fun getAllActiveAlbums(): List<Album> {
        return albumRepository.findAllByIsDeletedFalseOrderByReleaseDate()
    }

    /**
     * . 아티스트별 앨범 목록 조회
     */
    fun getAlbumsByArtist(artistId: Long): List<Album> {
        return albumRepository.findAllByArtistIdAndIsDeletedFalseOrderByReleaseDate(artistId)
    }

    /**
     * . 개별 앨범 조회
     */
    fun getAlbumById(id: Long): Album? {
        return albumRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Album with id $id not found")
    }

    /**
     * . 개별 앨범 신규 등록
     */
    @Transactional
    fun saveAlbum(albumDto: AlbumCreateRequestDto) {
        val artist = artistRepository.findByIdOrNull(albumDto.artistId)
            ?: throw ResourceNotFoundException("Artist with id ${albumDto.artistId} not found")

        albumRepository.save(
            Album(
                artist = artist,
                name = albumDto.name,
                type = AlbumType.valueOf(albumDto.type),
                releaseDate = albumDto.releaseDate,
                isRepackage = albumDto.isRepackage,
                isActive = albumDto.isActive
            )
        )
    }

    /**
     * . 개별 앨범 수정
     */
    @Transactional
    fun updateAlbum(id: Long, albumDto: AlbumUpdateRequestDto) {
        val album = albumRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Album with id $id not found")

        // 엔티티 업데이트
        album.updateAlbum(albumDto)
    }

    /**
     * . 개별 앨범 삭제
     */
    @Transactional
    fun deleteAlbum(id: Long) {
        val album = albumRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Album with id $id not found")

        // 엔티티 업데이트
        album.deleteAlbum()
    }
}