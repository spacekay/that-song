package com.spacekay.thatsong.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message)