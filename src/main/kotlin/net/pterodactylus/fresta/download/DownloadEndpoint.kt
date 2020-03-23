package net.pterodactylus.fresta.download

import io.ktor.features.BadRequestException

class DownloadEndpoint(private val downloadService: DownloadService) {

	fun download(key: String?): DownloadResult {
		if (key.isNullOrEmpty()) throw BadRequestException("Key is required")
		return downloadService.download(key)
	}

}
