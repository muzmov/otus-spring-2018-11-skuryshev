package ru.otus.reader

import java.io.BufferedReader
import java.io.InputStreamReader

class ConsoleReader: Reader {
    private val br = BufferedReader(InputStreamReader(System.`in`))

    override fun readLine() = br.readLine()
}