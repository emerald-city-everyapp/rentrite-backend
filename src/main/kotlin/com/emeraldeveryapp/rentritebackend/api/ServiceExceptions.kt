package com.emeraldeveryapp.rentritebackend.api

import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import java.lang.RuntimeException

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Rental profile not found.")
class ProfileNotFoundException : RuntimeException()

@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="Rental profile for specified address already exists.")
class PrfileAlreadyExistsException : RuntimeException()