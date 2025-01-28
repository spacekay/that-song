package com.spacekay.thatsong.domain.members.entity

import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberUpdateRequestDto
import jakarta.persistence.*
import java.time.LocalDate
import java.time.OffsetDateTime


@Entity
@Table(name = "members")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // 아이디

    @Column(name = "code", nullable = false, length = 60)
    var code: String, // 한글 이름

    @Column(name = "name", nullable = false)
    var name: String, // 한글 이름

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    var gender: Gender, // 해당 멤버의 성별

    @Column(name = "debut_date", nullable = false)
    var debutDate: LocalDate, // 데뷔 일자

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate, // 생일

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

    fun updateMember(memberDto: MemberUpdateRequestDto) {
        this.name = memberDto.name
        this.gender = Gender.valueOf(memberDto.gender)
        this.debutDate = memberDto.debutDate
        this.birthDate = memberDto.birthDate
        this.isActive = memberDto.isActive
    }

    fun deleteMember() {
        this.isDeleted = true
    }

}