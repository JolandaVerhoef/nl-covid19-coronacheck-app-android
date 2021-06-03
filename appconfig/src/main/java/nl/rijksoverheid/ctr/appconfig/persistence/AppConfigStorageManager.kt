/*
 *  Copyright (c) 2021 De Staat der Nederlanden, Ministerie van Volksgezondheid, Welzijn en Sport.
 *   Licensed under the EUROPEAN UNION PUBLIC LICENCE v. 1.2
 *
 *   SPDX-License-Identifier: EUPL-1.2
 *
 */

package nl.rijksoverheid.ctr.appconfig.persistence

import java.io.File
import java.io.IOException

interface AppConfigStorageManager {
    fun storageFile(file: File, fileContents: String): StorageResult
    fun areConfigFilesPresent(): Boolean
}

class AppConfigStorageManagerImpl(private val cacheDir: String): AppConfigStorageManager {
    override fun storageFile(file: File, fileContents: String): StorageResult {
        return try {
            file.parentFile?.mkdirs()
            file.bufferedWriter().use {
                it.write(fileContents)
            }
            StorageResult.Success
        } catch (exception: IOException) {
            StorageResult.Error(exception.message ?: "error storing: $file")
        }
    }

    override fun areConfigFilesPresent(): Boolean {
        val configFile = File(cacheDir, "config.json")
        val publicKeysFile = File(cacheDir, "public_keys.json")

        return configFile.exists() && publicKeysFile.exists()
    }
}

sealed class StorageResult {
    object Success: StorageResult()
    data class Error(val message: String): StorageResult()
}