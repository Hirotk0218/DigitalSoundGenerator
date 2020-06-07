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
        const val FREQ_C = 130.8127827
        const val FREQ_Df = 138.5913155
        const val FREQ_D = 146.832384
        const val FREQ_Ef = 155.563492
        const val FREQ_E = 164.813778
        const val FREQ_F = 174.614116
        const val FREQ_Gf = 184.997211
        const val FREQ_G = 195.997718
        const val FREQ_Af = 207.652349
        const val FREQ_A = 220.0
        const val FREQ_Hf = 233.081881
        const val FREQ_H = 246.941651

        const val OCT_FREQ_C = 261.625565
        const val OCT_FREQ_Df = 277.182630
        const val OCT_FREQ_D = 293.664767
        const val OCT_FREQ_Ef = 311.126983
        const val OCT_FREQ_E = 329.627556
        const val OCT_FREQ_F = 349.228231
        const val OCT_FREQ_Gf = 369.994227
        const val OCT_FREQ_G = 391.994535
        const val OCT_FREQ_Af = 415.304697
        const val OCT_FREQ_A = 440.0
        const val OCT_FREQ_Hf = 466.163761
        const val OCT_FREQ_H = 493.883301

        const val SEC_OCT_FREQ_C = 523.251131
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
        var waveDf = 0.0
        var waveD = 0.0
        var waveEf = 0.0
        var waveE = 0.0
        var waveF = 0.0
        var waveGf = 0.0
        var waveG = 0.0
        var waveAf = 0.0
        var waveA = 0.0
        var waveHf = 0.0
        var waveH = 0.0

        var octWaveC = 0.0
        var octWaveDf = 0.0
        var octWaveD = 0.0
        var octWaveEf = 0.0
        var octWaveE = 0.0
        var octWaveF = 0.0
        var octWaveGf = 0.0
        var octWaveG = 0.0
        var octWaveAf = 0.0
        var octWaveA = 0.0
        var octWaveHf = 0.0
        var octWaveH = 0.0

        var secOctWaveC = 0.0

        for (i in buffer.indices) {
            if (soundList[0].second[position].isSelected) {
                waveC = i / (this.sampleRate / FREQ_C) * (Math.PI * 2)
                waveC = sin(waveC)
            }
            if (soundList[1].second[position].isSelected) {
                waveDf = i / (this.sampleRate / FREQ_Df) * (Math.PI * 2)
                waveDf = sin(waveDf)
            }
            if (soundList[2].second[position].isSelected) {
                waveD = i / (this.sampleRate / FREQ_D) * (Math.PI * 2)
                waveD = sin(waveD)
            }
            if (soundList[3].second[position].isSelected) {
                waveEf = i / (this.sampleRate / FREQ_Ef) * (Math.PI * 2)
                waveEf = sin(waveEf)
            }
            if (soundList[4].second[position].isSelected) {
                waveE = i / (this.sampleRate / FREQ_E) * (Math.PI * 2)
                waveE = sin(waveE)
            }
            if (soundList[5].second[position].isSelected) {
                waveF = i / (this.sampleRate / FREQ_F) * (Math.PI * 2)
                waveF = sin(waveF)
            }
            if (soundList[6].second[position].isSelected) {
                waveGf = i / (this.sampleRate / FREQ_Gf) * (Math.PI * 2)
                waveGf = sin(waveGf)
            }
            if (soundList[7].second[position].isSelected) {
                waveG = i / (this.sampleRate / FREQ_G) * (Math.PI * 2)
                waveG = sin(waveG)
            }
            if (soundList[8].second[position].isSelected) {
                waveAf = i / (this.sampleRate / FREQ_Af) * (Math.PI * 2)
                waveAf = sin(waveAf)
            }
            if (soundList[9].second[position].isSelected) {
                waveA = i / (this.sampleRate / FREQ_A) * (Math.PI * 2)
                waveA = sin(waveA)
            }
            if (soundList[10].second[position].isSelected) {
                waveHf = i / (this.sampleRate / FREQ_Hf) * (Math.PI * 2)
                waveHf = sin(waveHf)
            }
            if (soundList[11].second[position].isSelected) {
                waveH = i / (this.sampleRate / FREQ_H) * (Math.PI * 2)
                waveH = sin(waveH)
            }

            if (soundList[12].second[position].isSelected) {
                octWaveC = i / (this.sampleRate / OCT_FREQ_C) * (Math.PI * 2)
                octWaveC = sin(octWaveC)
            }
            if (soundList[13].second[position].isSelected) {
                octWaveDf = i / (this.sampleRate / OCT_FREQ_Df) * (Math.PI * 2)
                octWaveDf = sin(octWaveDf)
            }
            if (soundList[14].second[position].isSelected) {
                octWaveD = i / (this.sampleRate / OCT_FREQ_D) * (Math.PI * 2)
                octWaveD = sin(octWaveD)
            }
            if (soundList[15].second[position].isSelected) {
                octWaveEf = i / (this.sampleRate / OCT_FREQ_Ef) * (Math.PI * 2)
                octWaveEf = sin(octWaveEf)
            }
            if (soundList[16].second[position].isSelected) {
                octWaveE = i / (this.sampleRate / OCT_FREQ_E) * (Math.PI * 2)
                octWaveE = sin(octWaveE)
            }
            if (soundList[17].second[position].isSelected) {
                octWaveF = i / (this.sampleRate / OCT_FREQ_F) * (Math.PI * 2)
                octWaveF = sin(octWaveF)
            }
            if (soundList[18].second[position].isSelected) {
                octWaveGf = i / (this.sampleRate / OCT_FREQ_Gf) * (Math.PI * 2)
                octWaveGf = sin(octWaveGf)
            }
            if (soundList[19].second[position].isSelected) {
                octWaveG = i / (this.sampleRate / OCT_FREQ_G) * (Math.PI * 2)
                octWaveG = sin(octWaveG)
            }
            if (soundList[20].second[position].isSelected) {
                octWaveAf = i / (this.sampleRate / OCT_FREQ_Af) * (Math.PI * 2)
                octWaveAf = sin(octWaveAf)
            }
            if (soundList[21].second[position].isSelected) {
                octWaveA = i / (this.sampleRate / OCT_FREQ_A) * (Math.PI * 2)
                octWaveA = sin(octWaveA)
            }
            if (soundList[22].second[position].isSelected) {
                octWaveHf = i / (this.sampleRate / OCT_FREQ_Hf) * (Math.PI * 2)
                octWaveHf = sin(octWaveHf)
            }
            if (soundList[23].second[position].isSelected) {
                octWaveH = i / (this.sampleRate / OCT_FREQ_H) * (Math.PI * 2)
                octWaveH = sin(octWaveH)
            }

            if (soundList[24].second[position].isSelected) {
                secOctWaveC = i / (this.sampleRate / SEC_OCT_FREQ_C) * (Math.PI * 2)
                secOctWaveC = sin(secOctWaveC)
            }

            val mixWave =
                waveC + waveDf + waveD + waveEf + waveE + waveF + waveGf + waveG + waveAf + waveA + waveHf + waveH +
                        octWaveC + octWaveDf + octWaveD + octWaveEf + octWaveE + octWaveF + octWaveGf + octWaveG + octWaveAf + octWaveA + octWaveHf + octWaveH + secOctWaveC

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