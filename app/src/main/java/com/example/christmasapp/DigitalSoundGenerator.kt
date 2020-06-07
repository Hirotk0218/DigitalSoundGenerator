package com.example.christmasapp

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.ceil
import kotlin.math.sin

/**
 * ドレミファソラシドの音階を作成する
 *
 * @param sampleRate サンプルレート
 * @param bufferSize バッファサイズ
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
                (if (wave > 0.0) java.lang.Byte.MAX_VALUE else java.lang.Byte.MIN_VALUE).toByte()
        }

        return buffer
    }

    /**
     * 合成波形データの作成
     * @param soundList 譜面データ
     * @param position 選択されている段落
     * @return 音声データ
     */
    fun getMixSound(
        soundList: ArrayList<Pair<Int, ArrayList<SoundDto>>>,
        position: Int
    ): ByteArray {
        // byteバッファを作成
        val buffer = ByteArray(ceil(bufferSize * 0.5).toInt())
        var waveC = 0.0
        var waveD = 0.0
        var waveE = 0.0
        var waveF = 0.0
        var waveG = 0.0
        var waveA = 0.0
        var waveB = 0.0

        for (i in buffer.indices) {
            if (soundList[0].second[position].isSelected) {
                waveC = i / (this.sampleRate / FREQ_C) * (Math.PI * 2)
                waveC = sin(waveC)
            }
            if (soundList[1].second[position].isSelected) {
                waveD = i / (this.sampleRate / FREQ_D) * (Math.PI * 2)
                waveD = sin(waveD)
            }
            if (soundList[2].second[position].isSelected) {
                waveE = i / (this.sampleRate / FREQ_E) * (Math.PI * 2)
                waveE = sin(waveE)
            }
            if (soundList[3].second[position].isSelected) {
                waveF = i / (this.sampleRate / FREQ_F) * (Math.PI * 2)
                waveF = sin(waveF)
            }
            if (soundList[4].second[position].isSelected) {
                waveG = i / (this.sampleRate / FREQ_G) * (Math.PI * 2)
                waveG = sin(waveG)
            }
            if (soundList[5].second[position].isSelected) {
                waveA = i / (this.sampleRate / FREQ_A) * (Math.PI * 2)
                waveA = sin(waveA)
            }
            if (soundList[6].second[position].isSelected) {
                waveB = i / (this.sampleRate / FREQ_B) * (Math.PI * 2)
                waveB = sin(waveB)
            }

            val mixWave = waveA + waveB + waveC + waveD + waveE + waveF + waveG

            buffer[i] =
                (if (mixWave > 0.0) buffer[i] + java.lang.Byte.MAX_VALUE else buffer[i] - java.lang.Byte.MIN_VALUE).toByte()
        }

        return buffer
    }

    /**
     * いわゆる休符
     * @param soundLength 音の長さ
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