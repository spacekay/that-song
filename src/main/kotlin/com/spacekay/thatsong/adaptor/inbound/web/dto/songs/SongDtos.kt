package com.spacekay.thatsong.adaptor.inbound.web.dto.songs

import com.spacekay.thatsong.domain.songs.entity.Genre
import com.spacekay.thatsong.domain.songs.entity.Song
import com.spacekay.thatsong.extentions.StringArrayExtension.arrayToString
import java.time.LocalDateTime

// 곡 목록 응답 DTO
data class SongSummaryDto(
    val id: Long? = null,
    val nameKr: String,
    val nameEn: String,
    val keywords: String? = null,
    val hasMv: Boolean,
    val lyricsSnippet: String? = null,
) {
    constructor(song: Song) : this(
        song.id,
        song.nameKr,
        song.nameEn,
        arrayToString(song.keywords),
        song.hasMv,
        (song.lyrics?.take(10) + "...")
    )
}

// 곡 개별 응답 DTO
data class SongDetailsDto(
    val id: Long? = null,
    val nameKr: String,
    val nameEn: String,
    val keywords: List<String>? = ArrayList(),
    val lyrics: String? = null,
    val hasMv: Boolean? = null,
    val hasMusicShow: Boolean? = null,
    val hasOfficialClip: Boolean? = null,
    val isConcertSetList: Boolean? = null,
    val presentGenres: List<Genre>? = ArrayList(),
    val moods: List<String>? = ArrayList(),
    val introMemberId: Long? = null,
    val introMemberName: String? = null,
    val isActive: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    constructor(song: Song) : this(
        song.id,
        song.nameKr,
        song.nameEn,
        song.keywords,
        song.lyrics,
        song.hasMv,
        song.hasMusicShow,
        song.hasOfficialClip,
        song.isConcertSetList,
        song.genres.map { Genre.valueOf(it) },
        song.moods,
        song.introMember?.id,
        song.introMember?.name,
        song.isActive,
        song.createdAt?.toLocalDateTime()?.plusHours(9)?.withNano(0),
        song.updatedAt?.toLocalDateTime()?.plusHours(9)?.withNano(0)
    )
}

// 곡 개별 등록 및 수정 요청 DTO
data class SongRequestDto(
    val nameKr: String,
    val nameEn: String,
    val keywords: List<String> = ArrayList(),
    val lyrics: String? = null,
    val hasMv: Boolean,
    val hasMusicShow: Boolean,
    val hasOfficialClip: Boolean,
    val isConcertSetList: Boolean,
    val genres: List<String> = ArrayList(),
    val moods: List<String> = ArrayList(),
    val isActive: Boolean,
)
