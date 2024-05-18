package com.emeraldeveryapp.rentritebackend

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.concurrent.atomic.AtomicLong


@CrossOrigin()
@RestController
class GreetingController {
    val template = "Hello %s!"
    val counter: AtomicLong = AtomicLong()
    
    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting(counter.incrementAndGet(), String.format(template, name))
    }
}