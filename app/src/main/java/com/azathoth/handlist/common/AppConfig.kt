package com.azathoth.handlist.common

object AppConfig {
    private const val API_VERSION = "v1"
    private const val LOCAL_IP = "10.0.2.2"
    private const val PORT = "8080"
    const val LOCAL_BASE_URL = "http://$LOCAL_IP:$PORT/api/$API_VERSION/"
}