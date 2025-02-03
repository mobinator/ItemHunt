package org.nico.itemHunt.utils

object CsvReader {

    fun readCsvFile(path: String): List<String> {
        val file = java.io.File(path)
        val lines = file.readLines()
        return lines.map { it.split(",")[0] }
    }

}