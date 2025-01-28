package com.spacekay.thatsong.adaptor.inbound.web.controller.artists

import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumSummaryDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistDetailsDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistSummaryDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.artists.ArtistUpdateRequestDto
import com.spacekay.thatsong.domain.albums.service.AlbumService
import com.spacekay.thatsong.domain.artists.service.ArtistService
import com.spacekay.thatsong.domain.members.service.MemberService
import com.spacekay.thatsong.exception.ResourceNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/admin/artists"])
@Controller
class AdminArtistController(
    private val artistService: ArtistService,
    private val albumService: AlbumService,
    private val memberService: MemberService
) {
    @GetMapping
    fun listArtists(model: Model): String {
        // 아티스트 목록 조회
        val artists = artistService.getAllActiveArtists().map { ArtistSummaryDto(it) }

        model.addAttribute("artists", artists)
        return "artists/artist-list"
    }

    @GetMapping(value = ["/{id}"])
    fun getArtistEditPage(@PathVariable id: Long, model: Model): String {
        // 개별 아티스트 조회
        try {
            val artist = artistService.getArtistById(id)?.let { ArtistDetailsDto(it) }

            model.addAttribute("artist", artist)
        } catch (e: ResourceNotFoundException) {
            return "artists/artist-edit"
        }

        // 앨범 목록 조회
        val albums = albumService.getAlbumsByArtist(id).map { AlbumSummaryDto(it) }

        model.addAttribute("albums", albums)

        // 멤버 목록 조회
        val members = memberService.getMembersByArtistId(id)

        model.addAttribute("members", members)
        return "artists/artist-edit"
    }

    @GetMapping(value = ["/create"])
    fun getArtistCreatePage(model: Model): String {
        return "artists/artist-create"
    }

    @PostMapping(value = ["/create"])
    fun createArtist(@ModelAttribute artistDto: ArtistCreateRequestDto, model: Model): String {
        artistService.saveArtist(artistDto)
        return "redirect:/admin/artists"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editArtist(@PathVariable id: Long, @ModelAttribute artistDto: ArtistUpdateRequestDto, model: Model): String {
        // 개별 아티스트 조회
        try {
            artistService.updateArtist(id, artistDto)
            return "redirect:/admin/artists"
        } catch (e: ResourceNotFoundException) {
            return "artists/artist-edit"
        } catch (e: RuntimeException) {
            throw e
        }
    }

    @GetMapping(value = ["/{id}/delete"])
    fun deleteArtist(@PathVariable id: Long): String {
        // 개별 아티스트 조회
        artistService.deleteArtist(id)
        return "redirect:/admin/artists"
    }
}