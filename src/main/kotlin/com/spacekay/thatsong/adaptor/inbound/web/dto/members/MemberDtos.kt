package com.spacekay.thatsong.adaptor.inbound.web.dto.members

import com.spacekay.thatsong.domain.members.entity.Member
import java.time.LocalDate
import java.time.LocalDateTime

// 아티스트 목록 응답 DTO
data class MemberSummaryDto(
    val id: Long? = null,
    val name: String,
    val code: String,
    val gender: String,
    val birthDate: LocalDate,
) {
    constructor(member: Member) : this(member.id, member.name, member.code, member.gender.toString(), member.birthDate)
}

// 아티스트 개별 응답 DTO
data class MemberDetailsDto(
    val id: Long? = null,
    val name: String,
    val code: String,
    val gender: String,
    val debutDate: LocalDate,
    val birthDate: LocalDate,
    val isActive: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    constructor(member: Member) : this(
        member.id,
        member.name,
        member.code,
        member.gender.toString(),
        member.debutDate,
        member.birthDate,
        member.isActive,
        member.createdAt?.toLocalDateTime()?.plusHours(9)?.withNano(0),
        member.updatedAt?.toLocalDateTime()?.plusHours(9)?.withNano(0)
    )
}

// 아티스트 개별 등록 요청 DTO
data class MemberCreateRequestDto(
    val name: String,
    val code: String,
    val gender: String,
    val debutDate: LocalDate,
    val birthDate: LocalDate,
    val isActive: Boolean,
)

// 아티스트 개별 수정 요청 DTO
data class MemberUpdateRequestDto(
    val name: String,
    val gender: String,
    val debutDate: LocalDate,
    val birthDate: LocalDate,
    val isActive: Boolean,
)