package ru.otus.pollserviceboot.service

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class ShellComponent(private val service: PollService) {

    @ShellMethod("Start poll")
    fun startPoll(@ShellOption(defaultValue = "") name: String) {
        if (name.isEmpty()) service.doPoll() else service.doPoll(name)
    }
}