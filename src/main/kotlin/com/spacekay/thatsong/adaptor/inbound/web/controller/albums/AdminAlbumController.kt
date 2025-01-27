package com.spacekay.thatsong.adaptor.inbound.web.controller.albums

import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumCreateRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumDetailsDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumSummaryDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.albums.AlbumUpdateRequestDto
import com.spacekay.thatsong.domain.albums.entity.AlbumType
import com.spacekay.thatsong.domain.albums.service.AlbumService
import com.spacekay.thatsong.domain.artists.service.ArtistService
import com.spacekay.thatsong.exception.ResourceNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/admin/albums"])
@Controller
class AdminAlbumController(
    private val albumService: AlbumService,
    private val artistService: ArtistService
) {
    @GetMapping
    fun listAlbums(model: Model): String {
        // 앨범 목록 조회
        val albums = albumService.getAllActiveAlbums().map { AlbumSummaryDto(it) }

        model.addAttribute("albums", albums)
        return "albums/album-main"
    }

    @GetMapping(value = ["/{id}"])
    fun getAlbumEditPage(@PathVariable id: Long, model: Model): String {
        // 개별 앨범 조회
        try {
            // Artist 목록을 전달
            val artists = artistService.getAllActiveArtists()
            model.addAttribute("artists", artists)

            val album = albumService.getAlbumById(id)

            // Enum 값을 Map으로 변환
            val albumTypes = AlbumType.entries.map {
                mapOf(
                    "name" to it.name, "value" to it.nameKr,
                    "isSelected" to (it.name == album?.type?.name)
                )
            }
            model.addAttribute("albumTypes", albumTypes)
            model.addAttribute("album", album?.let { AlbumDetailsDto(it) })
        } catch (e: ResourceNotFoundException) {
            return "albums/album-edit"
        }
        return "albums/album-edit"
    }

    @GetMapping(value = ["/create"])
    fun getAlbumCreatePage(model: Model): String {
        // Artist 목록을 전달
        val artists = artistService.getAllActiveArtists()
        model.addAttribute("artists", artists)

        // Enum 값을 Map으로 변환
        val albumTypes = AlbumType.entries.map {
            mapOf("name" to it.name, "value" to it.nameKr)
        }
        model.addAttribute("albumTypes", albumTypes)
        return "albums/album-create"
    }

    @PostMapping(value = ["/create"])
    fun createAlbum(@ModelAttribute albumDto: AlbumCreateRequestDto): String {
        albumService.saveAlbum(albumDto)
        return "redirect:/admin/albums"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editAlbum(@PathVariable id: Long, @ModelAttribute albumDto: AlbumUpdateRequestDto, model: Model): String {
        // 개별 앨범 조회
        try {
            albumService.updateAlbum(id, albumDto)
            return "redirect:/admin/albums"
        } catch (e: ResourceNotFoundException) {
            return "albums/album-edit"
        } catch (e: RuntimeException) {
            throw e
        }
    }

    @GetMapping(value = ["/{id}/delete"])
    fun deleteAlbum(@PathVariable id: Long): String {
        // 개별 앨범 조회
        albumService.deleteAlbum(id)
        return "redirect:/admin/albums"
    }
}