package com.spacekay.thatsong.domain.artists.entity

import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistUpdateRequestDto
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime

@Entity
@Table(name = "artists")
class Artist(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // 아이디

    @Column(nullable = false, unique = true, length = 30)
    var code: String, // 아티스트 코드
    
    @Column(name = "name_kr", nullable = false)
    var nameKr: String, // 한글 이름

    @Column(name = "name_en", nullable = false)
    var nameEn: String, // 영어 이름

    @Column(name = "debut_date", nullable = false)
    var debutDate: LocalDate, // 데뷔 날짜

    @Column(name = "is_group", nullable = false)
    var isGroup: Boolean = true, // 그룹 여부

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true, // 활동 여부

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false, // 삭제 여부

    @Column(name = "created_at", updatable = false)
    var createdAt: OffsetDateTime? = null, // 생성일시

    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = null // 수정일시
) {
    constructor(artistDto: ArtistCreateRequestDto) : this(
        code = artistDto.code,
        nameKr = artistDto.nameKr,
        nameEn = artistDto.nameEn,
        debutDate = artistDto.debutDate,
        isGroup = artistDto.isGroup,
        isActive = artistDto.isActive,
    )

    @PrePersist
    fun onPrePersist() {
        val now = OffsetDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = OffsetDateTime.now()
    }

    fun updateArtist(artistDto: ArtistUpdateRequestDto) {
        this.nameKr = artistDto.nameKr
        this.nameEn = artistDto.nameEn
        this.debutDate = artistDto.debutDate
        this.isGroup = artistDto.isGroup
        this.isActive = artistDto.isActive
    }

    fun deleteArtist() {
        this.isDeleted = true
    }

}