package net.pterodactylus.fresta.download

class DownloadEndpoint(private val downloadService: DownloadService) {

	fun download(key: String): DownloadResult {
		return downloadService.download(key)
	}

}
