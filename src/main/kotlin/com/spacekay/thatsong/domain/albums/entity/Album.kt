package com.spacekay.thatsong.domain.albums.entity

import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumUpdateRequestDto
import com.spacekay.thatsong.domain.artists.entity.Artist
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime


@Entity
@Table(name = "albums")
class Album(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // 아이디

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "artist_id", nullable = false) // 외래 키 이름 지정
    val artist: Artist, // 연관된 Artist 엔터티

    @Column(name = "name", nullable = false)
    var name: String, // 한글 이름

    @Column(name = "release_date", nullable = false)
    var releaseDate: LocalDate, // 발매 일자

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: AlbumType, // 해당 앨범의 타입

    @Column(name = "is_repackage", nullable = false)
    var isRepackage: Boolean = false, // 리패키지 여부

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true, // 활동 여부

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false, // 삭제 여부

    @Column(name = "created_at", updatable = false)
    var createdAt: OffsetDateTime? = null, // 생성일시

    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = null // 수정일시
) {
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

    fun updateAlbum(albumDto: AlbumUpdateRequestDto) {
        this.name = albumDto.name
        this.type = AlbumType.valueOf(albumDto.type)
        this.releaseDate = albumDto.releaseDate
        this.isRepackage = albumDto.isRepackage
        this.isActive = albumDto.isActive
    }

    fun deleteAlbum() {
        this.isDeleted = true
    }

}