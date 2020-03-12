package net.pterodactylus.fresta.download

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.pterodactylus.fcp.highlevel.FcpClient
import java.io.File
import java.util.concurrent.ConcurrentHashMap

class FcpDownloadService(private val fcpClient: FcpClient) : DownloadService {

	private val downloads = ConcurrentHashMap<String, DownloadResult>()

	override fun download(key: String): DownloadResult {
		var downloadResult = downloads[key]
		if (downloadResult != null) return downloadResult

		downloadResult = DownloadResult()
		downloads[key] = downloadResult
		GlobalScope.launch {
			try {
				val getResult = fcpClient.getURI(key, false)
				if (getResult.isSuccess && getResult.inputStream != null) {
					val dir = File(DOWNLOADS_PATH)
					dir.mkdirs()
					val filename = key.substringAfterLast('/')
					File(dir, filename).outputStream().use {
						getResult.inputStream.copyTo(it)
					}
					getResult.inputStream.close()

					downloads[key] = DownloadResult(true, true, filename)
				} else {
					downloads[key] = DownloadResult(true, false)
				}
			} catch (e: Exception) {
				e.printStackTrace() // TODO
			}
		}
		return downloadResult
	}

}
