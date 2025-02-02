package com.spacekay.thatsong.domain.songs.entity

import com.spacekay.thatsong.adaptor.inbound.web.dto.songs.SongRequestDto
import com.spacekay.thatsong.domain.members.entity.Member
import com.spacekay.thatsong.extentions.StringArrayExtension.refineStringArray
import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.time.OffsetDateTime

@Entity
@Table(name = "songs")
data class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name_kr", nullable = false)
    var nameKr: String,

    @Column(name = "name_en", nullable = false)
    var nameEn: String,

    @Type(ListArrayType::class)
    @Column(name = "keywords", columnDefinition = "text[]")
    var keywords: List<String>,

    @Column(name = "lyrics")
    var lyrics: String? = null,

    @Column(name = "has_mv", nullable = false)
    var hasMv: Boolean = false,

    @Column(name = "has_music_show", nullable = false)
    var hasMusicShow: Boolean = false,

    @Column(name = "has_official_clip", nullable = false)
    var hasOfficialClip: Boolean = false,

    @Column(name = "is_concert_set_list", nullable = false)
    var isConcertSetList: Boolean = false,

    @Type(ListArrayType::class)
    @Column(name = "genres", columnDefinition = "text[]")
    var genres: List<String>,

    @Type(ListArrayType::class)
    @Column(name = "moods", columnDefinition = "text[]")
    var moods: List<String>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intro_member_id")
    var introMember: Member? = null, // Member 엔티티와 관계 설정

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

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

    fun updateSong(songDto: SongRequestDto) {
        this.nameKr = songDto.nameKr
        this.nameEn = songDto.nameEn
        this.keywords = refineStringArray(songDto.keywords)
        this.genres = refineStringArray(songDto.genres)
        this.moods = refineStringArray(songDto.moods)
        this.lyrics = songDto.lyrics
        this.hasMv = songDto.hasMv
        this.hasMusicShow = songDto.hasMusicShow
        this.hasOfficialClip = songDto.hasOfficialClip
        this.isConcertSetList = songDto.isConcertSetList
        this.isActive = songDto.isActive
    }

    fun deleteSong() {
        this.isDeleted = true
    }
}
