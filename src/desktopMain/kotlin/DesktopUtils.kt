@file:OptIn(ExperimentalForeignApi::class)

import kotlinx.cinterop.*
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import kotlin.system.getTimeMillis

actual fun readAllText(filePath: String): List<String> {
    val lines = mutableListOf<String>()
    val readMode = "r"
    val file = fopen(filePath, readMode) ?: throw IllegalArgumentException("Cannot open input file $filePath")

    try {
        memScoped {
            val readBufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line: String?
            do {
                line = fgets(buffer, readBufferLength, file)?.toKString()
                line?.let { lines.add(it.removeSuffix(lineSeparator())) }
            } while (line != null)
        }
    } finally {
        fclose(file)
    }
    return lines
}

actual fun getMillis() = getTimeMillis()