package net.pterodactylus.fresta.upload

import java.io.File

const val UPLOADS_PATH = "uploads"
const val IDENTIFIER_PREFIX = "fresta-"

interface UploadService {

	fun upload(file: File)

	fun check(identifier: String): UploadResult

}

data class UploadResult(
		var ready: Boolean = false,
		var message: String? = null,
		var key: String? = null
)
