package com.spacekay.thatsong.domain.artists.service

import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistUpdateRequestDto
import com.spacekay.thatsong.domain.artists.entity.Artist
import com.spacekay.thatsong.domain.artists.repository.ArtistRepository
import com.spacekay.thatsong.exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArtistService(private val artistRepository: ArtistRepository) {

    /**
     * . 아티스트 목록 조회
     */
    fun getAllActiveArtists(): List<Artist> {
        return artistRepository.findAllByIsDeletedFalse()
    }
    
    /**
     * . 개별 아티스트 조회
     */
    fun getArtistById(id: Long): Artist? {
        return artistRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("User with id $id not found")
    }

    /**
     * . 개별 아티스트 신규 등록
     */
    @Transactional
    fun saveArtist(artistDto: ArtistCreateRequestDto) {
        artistRepository.save(Artist(artistDto))
    }

    /**
     * . 개별 아티스트 수정
     */
    @Transactional
    fun updateArtist(id: Long, artistDto: ArtistUpdateRequestDto) {
        val artist = artistRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artist with id $id not found")

        // 엔티티 업데이트
        artist.updateArtist(artistDto)
    }

    /**
     * . 개별 아티스트 삭제
     */
    @Transactional
    fun deleteArtist(id: Long) {
        val artist = artistRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Artist with id $id not found")

        // 엔티티 업데이트
        artist.deleteArtist()
    }
}