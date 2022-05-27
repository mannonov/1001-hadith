package uz.h1001.hadith.core

interface SingleMapper<T, R> {
    operator fun invoke(value: T): R
}