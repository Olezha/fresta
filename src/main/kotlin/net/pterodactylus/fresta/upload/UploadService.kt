package net.pterodactylus.fresta.upload

import java.io.File

const val UPLOADS_PATH = "uploads"

interface UploadService {

	fun upload(file: File, title: String): UploadResult

}

data class UploadResult(
		var ready: Boolean = false,
		var message: String? = null,
		var key: String? = null
)
