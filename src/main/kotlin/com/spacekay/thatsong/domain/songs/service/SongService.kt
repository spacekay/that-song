package com.spacekay.thatsong.domain.songs.service

import com.spacekay.thatsong.adaptor.inbound.web.dto.songs.SongRequestDto
import com.spacekay.thatsong.domain.songs.entity.Song
import com.spacekay.thatsong.domain.songs.repository.SongRepository
import com.spacekay.thatsong.exception.ResourceNotFoundException
import com.spacekay.thatsong.extentions.StringArrayExtension.refineStringArray
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SongService(
    private val songRepository: SongRepository
) {
    /**
     * . 곡 목록 조회
     */
    fun getAllActiveSongs(): List<Song> {
        return songRepository.findAllByIsDeletedFalse()
    }

    /**
     * . 앨범별 곡 목록 조회
     */
//    fun getSongsByArtistId(artistId: Long): List<Song> {
//        return artistSongRepository.findAllByArtistId(artistId).map { it.song }
//    }

    /**
     * . 개별 곡 조회
     */
    fun getSongById(id: Long): Song? {
        return songRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Song with id $id not found")
    }

    /**
     * . 개별 곡 신규 등록
     */
    @Transactional
    fun saveSong(songDto: SongRequestDto) {
        songRepository.save(
            Song(
                nameKr = songDto.nameKr,
                nameEn = songDto.nameEn,
                keywords = refineStringArray(songDto.keywords),
                genres = refineStringArray(songDto.genres),
                moods = refineStringArray(songDto.moods),
                lyrics = songDto.lyrics,
                hasMv = songDto.hasMv,
                hasMusicShow = songDto.hasMusicShow,
                hasOfficialClip = songDto.hasOfficialClip,
                isConcertSetList = songDto.isConcertSetList,
                isActive = songDto.isActive
            )
        )
    }

    /**
     * . 개별 곡 수정
     */
    @Transactional
    fun updateSong(id: Long, songDto: SongRequestDto) {
        val song = songRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Song with id $id not found")

        // 엔티티 업데이트
        song.updateSong(songDto)
    }

    /**
     * . 개별 곡 삭제
     */
    @Transactional
    fun deleteSong(id: Long) {
        val song = songRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Song with id $id not found")

        // 엔티티 업데이트
        song.deleteSong()
    }
}