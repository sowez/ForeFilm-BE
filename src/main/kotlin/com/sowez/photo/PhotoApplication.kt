package com.sowez.photo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotoApplication

fun main(args: Array<String>) {
	runApplication<PhotoApplication>(*args)
}
