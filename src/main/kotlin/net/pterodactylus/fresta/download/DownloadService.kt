package net.pterodactylus.fresta.download

const val DOWNLOADS_PATH = "downloads"

interface DownloadService {

	fun download(key: String): DownloadResult

}

data class DownloadResult(
		var ready: Boolean = false,
		var success: Boolean = false,
		var message: String? = null
)
