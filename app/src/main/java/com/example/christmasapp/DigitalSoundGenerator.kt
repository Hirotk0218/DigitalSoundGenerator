package com.example.christmasapp

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.ceil
import kotlin.math.sin

/**
 * ドレミファソラシドの音階を作成する
 *
 * @param sampleRate
 * @param bufferSize
 */
class DigitalSoundGenerator(
    private val sampleRate: Int,
    private val bufferSize: Int
) {

    companion object {
        // とりあえず１オクターブ分の音階を確保（半音階含む）
        const val FREQ_C = 261.625565
        const val FREQ_Cs = 277.182630
        const val FREQ_D = 293.664767
        const val FREQ_Ds = 311.126983
        const val FREQ_E = 329.627556
        const val FREQ_F = 349.228231
        const val FREQ_Fs = 369.994227
        const val FREQ_G = 391.994535
        const val FREQ_Gs = 415.304697
        const val FREQ_A = 440.0
        const val FREQ_As = 466.163761
        const val FREQ_B = 493.883301
    }

    var audioTrack = AudioTrack.Builder()
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        .setAudioFormat(
            AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_DEFAULT)
                .setSampleRate(sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()
        )
        .setBufferSizeInBytes(bufferSize)
        .build()

    /**
     * 音の波形データの作成
     * @param frequency 鳴らしたい音の周波数
     * @param soundLength 音の長さ
     * @return 音声データ
     */
    fun getSound(frequency: Double, soundLength: Double): ByteArray {
        // byteバッファを作成
        val buffer = ByteArray(ceil(bufferSize * soundLength).toInt())
        for (i in buffer.indices) {
            var wave = i / (this.sampleRate / frequency) * (Math.PI * 2)
            wave = sin(wave)
            buffer[i] =
                (if ((i % wave) < wave / 2) java.lang.Byte.MAX_VALUE else java.lang.Byte.MIN_VALUE).toByte()
        }

        return buffer
    }

    /**
     * 合成波形データの作成
     * @param byte バイト
     * @param frequency 重ねたい音の周波数
     * @param soundLength 音の長さ
     * @return 音声データ
     */
    fun getMixSound(byte: Byte, frequency: Double, soundLength: Double): ByteArray {
        // byteバッファを作成
        val buffer = ByteArray(ceil(bufferSize * soundLength).toInt())
        for (i in buffer.indices) {
            var wave = i / (this.sampleRate / frequency) * (Math.PI * 2)
            wave = sin(wave)
            buffer[i] =
                (if ((i % wave) < wave / 2) java.lang.Byte.MAX_VALUE else java.lang.Byte.MIN_VALUE).toByte()
        }

        return buffer
    }

    /**
     * いわゆる休符
     * @param soundLength
     * @return 無音データ
     */
    fun getEmptySound(soundLength: Double): ByteArray {
        val buff = ByteArray(ceil(bufferSize * soundLength).toInt())

        for (i in buff.indices) {
            buff[i] = 0.toByte()
        }
        return buff
    }
}