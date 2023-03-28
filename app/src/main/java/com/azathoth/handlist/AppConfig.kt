package com.azathoth.handlist

object AppConfig {
    const val API_VERSION = "v1"
    private const val LOCAL_IP = "10.0.2.2"
    private const val PORT = "8080"
    const val BASE_URL = "https://$LOCAL_IP:$PORT/api/$API_VERSION"
}