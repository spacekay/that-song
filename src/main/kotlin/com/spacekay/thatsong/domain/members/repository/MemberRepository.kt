package com.spacekay.thatsong.domain.members.repository

import com.spacekay.thatsong.domain.members.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findAllByIsDeletedFalse(): List<Member>
}
