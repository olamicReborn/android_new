package com.maggnet.domain.remote

import java.io.IOException

class HTTPBadRequest constructor(message: String) : Throwable(message)

class HTTPNotFoundException constructor(message: String) : Exception(message)

class ServerNotAvailableException(message: String) : Throwable(message)

class AuthorizationException(message: String) : Throwable(message)

class NetworkException(throwable: Throwable) : IOException(throwable.message, throwable)


