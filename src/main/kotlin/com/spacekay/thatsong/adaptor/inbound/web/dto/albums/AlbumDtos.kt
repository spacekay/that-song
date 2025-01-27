package com.spacekay.thatsong.adaptor.inbound.web.dto.albums

import com.spacekay.thatsong.domain.albums.entity.Album
import java.time.LocalDate
import java.time.LocalDateTime


// 앨범 목록 응답 DTO
data class AlbumSummaryDto(
    val id: Long? = null,
    val artistId: Long? = null,
    val artistName: String? = null,
    val name: String,
    val type: String,
    val isRepackage: Boolean,
) {
    constructor(album: Album) : this(
        album.id, album.artist.id, album.artist.nameKr, album.name, album.type.toString(), album.isRepackage
    )
}

// 앨범 개별 응답 DTO
data class AlbumDetailsDto(
    val id: Long? = null,
    val artistId: Long? = null,
    val artistName: String? = null,
    val name: String,
    val type: String,
    val releaseDate: LocalDate,
    val isRepackage: Boolean,
    val isActive: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    constructor(album: Album) : this(
        album.id,
        album.artist.id,
        album.artist.nameKr,
        album.name,
        album.type.toString(),
        album.releaseDate,
        album.isRepackage,
        album.isActive,
        album.createdAt?.toLocalDateTime()?.plusHours(9)?.withNano(0),
        album.updatedAt?.toLocalDateTime()?.plusHours(9)?.withNano(0)
    )
}

// 앨범 개별 등록 요청 DTO
data class AlbumCreateRequestDto(
    val name: String,
    val artistId: Long? = null,
    val releaseDate: LocalDate,
    val type: String,
    val isRepackage: Boolean,
    val isActive: Boolean,
)

// 앨범 개별 수정 요청 DTO
data class AlbumUpdateRequestDto(
    val name: String,
    val releaseDate: LocalDate,
    val type: String,
    val isRepackage: Boolean,
    val isActive: Boolean,
)