package com.spacekay.thatsong.adaptor.inbound.web.controller.songs

import com.spacekay.thatsong.adaptor.inbound.web.dto.songs.SongDetailsDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.songs.SongRequestDto
import com.spacekay.thatsong.adaptor.inbound.web.dto.songs.SongSummaryDto
import com.spacekay.thatsong.domain.songs.entity.Genre
import com.spacekay.thatsong.domain.songs.service.SongService
import com.spacekay.thatsong.exception.ResourceNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/admin/songs"])
@Controller
class AdminSongController(private val songService: SongService) {
    @GetMapping
    fun listSongs(model: Model): String {
        // 곡 목록 조회
        val songs = songService.getAllActiveSongs().map { SongSummaryDto(it) }

        model.addAttribute("songs", songs)
        return "songs/song-main"
    }

    @GetMapping(value = ["/{id}"])
    fun getSongEditPage(@PathVariable id: Long, model: Model): String {
        // 개별 곡 조회
        try {
            val song = songService.getSongById(id)

            // Enum 값을 Map으로 변환
            val genres = Genre.entries.map {
                mapOf(
                    "name" to it.name, "value" to it.nameKr,
                )
            }

            model.addAttribute("genres", genres)
            model.addAttribute("song", song?.let { SongDetailsDto(it) })
        } catch (e: ResourceNotFoundException) {
            return "songs/song-edit"
        }

        return "songs/song-edit"
    }

    @GetMapping(value = ["/create"])
    fun getSongCreatePage(model: Model): String {
        // Enum 값을 Map으로 변환
        val genres = Genre.entries.map {
            mapOf("name" to it.name, "value" to it.nameKr)
        }
        model.addAttribute("genres", genres)
        return "songs/song-create"
    }

    @PostMapping(value = ["/create"])
    fun createSong(@ModelAttribute songDto: SongRequestDto, model: Model): String {
        println(songDto)
        songService.saveSong(songDto)
        return "redirect:/admin/songs"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editSong(@PathVariable id: Long, @ModelAttribute songDto: SongRequestDto, model: Model): String {
        // 개별 곡 조회
        try {
            songService.updateSong(id, songDto)
            return "redirect:/admin/songs"
        } catch (e: ResourceNotFoundException) {
            return "songs/song-edit"
        } catch (e: RuntimeException) {
            throw e
        }
    }

    @GetMapping(value = ["/{id}/delete"])
    fun deleteSong(@PathVariable id: Long): String {
        // 개별 곡 조회
        songService.deleteSong(id)
        return "redirect:/admin/songs"
    }
}