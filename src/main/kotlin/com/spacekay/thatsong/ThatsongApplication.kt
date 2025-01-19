package com.spacekay.thatsong

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThatSongApplication

fun main(args: Array<String>) {
	// Load .env.local file
	val dotenv = Dotenv.configure()
		.directory("src/main/resources") // .env.local 파일 경로
		.filename(".env.local")         // 파일 이름
		.load()

	// Set environment variables as system properties
	System.setProperty("DB_HOST", dotenv["DB_HOST"] ?: "localhost")
	System.setProperty("DB_PORT", dotenv["DB_PORT"] ?: "")
	System.setProperty("DB_NAME", dotenv["DB_NAME"] ?: "")
	System.setProperty("DB_USERNAME", dotenv["DB_USERNAME"] ?: "")
	System.setProperty("DB_PASSWORD", dotenv["DB_PASSWORD"] ?: "")

	runApplication<ThatSongApplication>(*args)
}
