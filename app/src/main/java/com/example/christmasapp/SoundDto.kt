package com.example.christmasapp

/**
 * 引数付きコンストラクタ
 * @param sound
 * @param length
 */
data class SoundDto(var sound: ByteArray, var length: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SoundDto

        if (!sound.contentEquals(other.sound)) return false
        if (length != other.length) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sound.contentHashCode()
        result = 31 * result + length.hashCode()
        return result
    }
}