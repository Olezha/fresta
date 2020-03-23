package net.pterodactylus.fresta.upload

import net.pterodactylus.fcp.highlevel.FcpClient
import java.io.File

class FcpUploadService(private val fcpClient: FcpClient) : UploadService {

	override fun upload(file: File, title: String): UploadResult {
		TODO("Not yet implemented")
	}

}
