package ru.otus.pollserviceboot.service

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class ConsoleReader: Reader {
    private val br = BufferedReader(InputStreamReader(System.`in`))

    override fun readLine() = br.readLine()
}