package com.spacekay.thatsong.adaptor.inbound.web.controller.members

import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberDetailsDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberSummaryDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.members.MemberUpdateRequestDto
import com.spacekay.thatsong.domain.members.entity.Gender
import com.spacekay.thatsong.domain.members.service.MemberService
import com.spacekay.thatsong.exception.ResourceNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/admin/members"])
@Controller
class AdminMemberController(private val memberService: MemberService) {
    @GetMapping
    fun listMembers(model: Model): String {
        // 멤버 목록 조회
        val members = memberService.getAllActiveMembers().map { MemberSummaryDto(it) }

        model.addAttribute("members", members)
        return "members/member-main"
    }

    @GetMapping(value = ["/{id}"])
    fun getMemberEditPage(@PathVariable id: Long, model: Model): String {
        // 개별 멤버 조회
        try {
            val member = memberService.getMemberById(id)

            // Enum 값을 Map으로 변환
            val genders = Gender.entries.map {
                mapOf(
                    "name" to it.name, "value" to it.nameKr,
                    "isSelected" to (it.name == member?.gender?.name)
                )
            }
            model.addAttribute("genders", genders)
            model.addAttribute("member", member?.let { MemberDetailsDto(it) })
        } catch (e: ResourceNotFoundException) {
            return "members/member-edit"
        }

        return "members/member-edit"
    }

    @GetMapping(value = ["/create"])
    fun getMemberCreatePage(model: Model): String {
        // Enum 값을 Map으로 변환
        val genders = Gender.entries.map {
            mapOf("name" to it.name, "value" to it.nameKr)
        }
        model.addAttribute("genders", genders)
        return "members/member-create"
    }

    @PostMapping(value = ["/create"])
    fun createMember(@ModelAttribute memberDto: MemberCreateRequestDto, model: Model): String {
        memberService.saveMember(memberDto)
        return "redirect:/admin/members"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editMember(@PathVariable id: Long, @ModelAttribute memberDto: MemberUpdateRequestDto, model: Model): String {
        // 개별 멤버 조회
        try {
            memberService.updateMember(id, memberDto)
            return "redirect:/admin/members"
        } catch (e: ResourceNotFoundException) {
            return "members/member-edit"
        } catch (e: RuntimeException) {
            throw e
        }
    }

    @GetMapping(value = ["/{id}/delete"])
    fun deleteMember(@PathVariable id: Long): String {
        // 개별 멤버 조회
        memberService.deleteMember(id)
        return "redirect:/admin/members"
    }

}