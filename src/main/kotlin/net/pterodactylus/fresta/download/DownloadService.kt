package net.pterodactylus.fresta.download

import java.io.File

const val DOWNLOADS_PATH = "downloads"

interface DownloadService {

	fun download(key: String): DownloadResult

}

data class DownloadResult(
		var ready: Boolean = false,
		var message: String? = null,
		var file: File? = null
)
