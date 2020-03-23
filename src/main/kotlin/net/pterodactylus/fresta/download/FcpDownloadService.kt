package net.pterodactylus.fresta.download

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.pterodactylus.fcp.highlevel.FcpClient
import net.pterodactylus.fcp.highlevel.GetResult
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
			var getResult: GetResult? = null
			try {
				getResult = fcpClient.getURI(key, false)
				if (getResult.isSuccess && getResult.inputStream != null) {
					val dir = File(DOWNLOADS_PATH + File.separator
							+ key.substringBeforeLast('/').replace("/", File.separator))
					dir.mkdirs()
					val file = File(dir, key.substringAfterLast('/'))
					file.outputStream().use {
						getResult.inputStream.copyTo(it)
					}

					downloads[key] = DownloadResult(ready = true, file = file)
				} else {
					downloads[key] = DownloadResult(ready = true, message = getResult.toString())
				}
			} catch (e: Exception) {
				downloads[key] = DownloadResult(ready = true, message = "$e ${e.message}")
			} finally {
				if (getResult != null && getResult.inputStream != null) {
					getResult.inputStream.close()
				}
			}
		}
		return downloadResult
	}

}
