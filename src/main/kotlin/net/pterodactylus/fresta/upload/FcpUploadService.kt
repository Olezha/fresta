package net.pterodactylus.fresta.upload

import net.pterodactylus.fcp.ClientPut
import net.pterodactylus.fcp.TestDDARequest
import net.pterodactylus.fcp.UploadFrom
import net.pterodactylus.fcp.highlevel.FcpClient
import java.io.File

class FcpUploadService(private val fcpClient: FcpClient) : UploadService {

	override fun upload(file: File) {
		val testDDA = TestDDARequest(file.parent, true, false)
		fcpClient.connection.sendMessage(testDDA)

		val put = ClientPut("CHK@", "$IDENTIFIER_PREFIX${file.parentFile.name}", UploadFrom.disk)
		put.setFilename(file.absolutePath)
		put.setTargetFilename(file.name)
		put.setGetCHKOnly(false)
		put.setGlobal(false)
		fcpClient.connection.sendMessage(put)
	}

	override fun check(identifier: String): UploadResult {
		val requests = fcpClient.getPutRequests(false)
		val request = requests.find { it.identifier == "$IDENTIFIER_PREFIX$identifier" } ?: return UploadResult(ready = true)
		if (request.isComplete) {
			return UploadResult(ready = true, key = "TODO")
		}
		return UploadResult(ready = false)
	}

}
