package com.spacekay.thatsong.adaptor.inbound.web.dto.artists

import com.spacekay.thatsong.domain.artists.entity.Artist
import java.time.LocalDate
import java.time.LocalDateTime

// 아티스트 목록 응답 DTO
data class ArtistSummaryDto(
    val id: Long? = null,
    val nameKr: String,
    val nameEn: String,
    val code: String,
    val isGroup: Boolean,
) {
    constructor(artist: Artist) : this(artist.id, artist.nameKr, artist.nameEn, artist.code, artist.isGroup)
}

// 아티스트 개별 응답 DTO
data class ArtistDetailsDto(
    val id: Long? = null,
    val nameKr: String,
    val nameEn: String,
    val code: String,
    val debutDate: LocalDate,
    val isGroup: Boolean,
    val isActive: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    constructor(artist: Artist) : this(
        artist.id,
        artist.nameKr,
        artist.nameEn,
        artist.code,
        artist.debutDate,
        artist.isGroup,
        artist.isActive,
        artist.createdAt?.toLocalDateTime()?.plusHours(9)?.withNano(0),
        artist.updatedAt?.toLocalDateTime()?.plusHours(9)?.withNano(0)
    )
}

// 아티스트 개별 등록 요청 DTO
data class ArtistCreateRequestDto(
    val nameKr: String,
    val nameEn: String,
    val code: String,
    val debutDate: LocalDate,
    val isGroup: Boolean,
    val isActive: Boolean,
)

// 아티스트 개별 수정 요청 DTO
data class ArtistUpdateRequestDto(
    val nameKr: String,
    val nameEn: String,
    val debutDate: LocalDate,
    val isGroup: Boolean,
    val isActive: Boolean,
)