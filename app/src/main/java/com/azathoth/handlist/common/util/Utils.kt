package com.azathoth.handlist.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//Int to Enum
inline fun <reified T : Enum<T>> Int.toEnum(): T? {
    return enumValues<T>().firstOrNull { it.ordinal == this }
}

//Enum to Int
inline fun <reified T : Enum<T>> T.toInt(): Int {
    return this.ordinal
}

// generate random string in the given length
fun getRandomStr(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")

}

enum class DateStatus {
    Today,
    Overdue,
    Next,
    NoDueDate
}

fun LocalDate?.toDateStatus() =
    when {
        this == null -> DateStatus.NoDueDate
        this.isBefore(LocalDate.now()) -> DateStatus.Overdue
        this.isEqual(LocalDate.now()) -> DateStatus.Today
        else -> DateStatus.Next
    }

fun getRandomStr8(): String = getRandomStr(8)

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun String.toLocalDateTime(): LocalDateTime = LocalDateTime.parse(this, formatter)

fun String.toLocalDate(): LocalDate = LocalDate.parse(this, formatter)

fun LocalDateTime.format(): String = this.format(formatter)

fun LocalDate.format(): String = this.format(formatter)