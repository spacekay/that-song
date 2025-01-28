package com.spacekay.thatsong.domain.members.entity

import com.spacekay.thatsong.domain.artists.entity.Artist
import jakarta.persistence.*
import java.time.OffsetDateTime


@Entity
@Table(name = "artists_members")
class ArtistMember(

    @EmbeddedId
    val id: ArtistMemberKey,

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @MapsId("artistId")
    @JoinColumn(name = "artist_id", nullable = false) // 외래 키 이름 지정
    val artist: Artist, // 연관된 Artist 엔터티

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false) // 외래 키 이름 지정
    val member: Member, // 연관된 Member 엔터티

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true, // 활동 여부

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

}