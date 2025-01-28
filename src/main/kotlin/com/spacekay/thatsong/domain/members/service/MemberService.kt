package com.spacekay.thatsong.domain.members.service

import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberUpdateRequestDto
import com.spacekay.thatsong.domain.members.entity.Gender
import com.spacekay.thatsong.domain.members.entity.Member
import com.spacekay.thatsong.domain.members.repository.ArtistMemberRepository
import com.spacekay.thatsong.domain.members.repository.MemberRepository
import com.spacekay.thatsong.exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val artistMemberRepository: ArtistMemberRepository
) {

    /**
     * . 멤버 목록 조회
     */
    fun getAllActiveMembers(): List<Member> {
        return memberRepository.findAllByIsDeletedFalse()
    }

    /**
     * . 아티스트별 멤버 목록 조회 -> 추후 맵핑 데이터 가져오는 법 확인 후 진행
     */
    fun getMembersByArtistId(artistId: Long): List<Member> {
        return artistMemberRepository.findAllByArtistId(artistId).map { it.member }
    }

    /**
     * . 개별 멤버 조회
     */
    fun getMemberById(id: Long): Member? {
        return memberRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Member with id $id not found")
    }

    /**
     * . 개별 멤버 신규 등록
     */
    @Transactional
    fun saveMember(memberDto: MemberCreateRequestDto) {
        memberRepository.save(
            Member(
                name = memberDto.name,
                code = memberDto.code,
                gender = Gender.valueOf(memberDto.gender),
                debutDate = memberDto.debutDate,
                birthDate = memberDto.birthDate,
                isActive = memberDto.isActive
            )
        )
    }

    /**
     * . 개별 멤버 수정
     */
    @Transactional
    fun updateMember(id: Long, memberDto: MemberUpdateRequestDto) {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Member with id $id not found")

        // 엔티티 업데이트
        member.updateMember(memberDto)
    }

    /**
     * . 개별 멤버 삭제
     */
    @Transactional
    fun deleteMember(id: Long) {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Album with id $id not found")

        // 엔티티 업데이트
        member.deleteMember()
    }
}