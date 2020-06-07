package com.example.christmasapp

/**
 * 引数付きコンストラクタ
 * @param sound 音の情報
 * @param length 音の長さ
 * @param isSelected 音が選択されているか
 */
data class SoundDto(var sound: ByteArray, var length: Double, var isSelected: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SoundDto

        if (!sound.contentEquals(other.sound)) return false
        if (length != other.length) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sound.contentHashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}