package com.example.christmasapp

import android.media.AudioTrack
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Runnable {

    companion object {
        // 音符の長さ
        const val EIGHTH_NOTE = 0.125
        const val FORTH_NOTE = 0.25
        const val HALF_NOTE = 0.5
        const val WHOLE_NOTE = 1.0
    }

    // Sound生成クラス
    private var soundGenerator: DigitalSoundGenerator? = null
    // Sound再生クラス
    private var audioTrack: AudioTrack? = null
    // 譜面データ
    private val soundList = ArrayList<SoundDto>()

    /**
     * 譜面データを作成
     */
    private fun initScoreData() {
        // 譜面データ作成
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, EIGHTH_NOTE), EIGHTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, EIGHTH_NOTE), EIGHTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_G,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_C,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, FORTH_NOTE), FORTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, FORTH_NOTE), FORTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_G,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, HALF_NOTE), HALF_NOTE))


        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, EIGHTH_NOTE), EIGHTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, EIGHTH_NOTE), EIGHTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_G,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_C,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(soundGenerator!!, DigitalSoundGenerator.FREQ_E, 3.0),
                3.0
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, FORTH_NOTE), FORTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
        soundList.add(SoundDto(generateEmptySound(soundGenerator!!, EIGHTH_NOTE), EIGHTH_NOTE))

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    EIGHTH_NOTE
                ), EIGHTH_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    EIGHTH_NOTE
                ), EIGHTH_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_E,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )

        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_G,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_G,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_F,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_D,
                    HALF_NOTE
                ), HALF_NOTE
            )
        )
        soundList.add(
            SoundDto(
                generateSound(
                    soundGenerator!!,
                    DigitalSoundGenerator.FREQ_C,
                    WHOLE_NOTE
                ), WHOLE_NOTE
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SoundGeneratorクラスをサンプルレート44100で作成
        soundGenerator = DigitalSoundGenerator(44100, 44100)

        // 再生用AudioTrackは、同じサンプルレートで初期化したものを利用する
        audioTrack = soundGenerator!!.audioTrack
        startMelody.setOnClickListener {
            // start sound
            val th = Thread(this)
            th.start()
        }

        // スコアデータを作成
        initScoreData()
    }

    override fun onDestroy() {
        super.onDestroy()

        // 再生中だったら停止してリリース
        if (audioTrack!!.playState == AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack!!.stop()
            audioTrack!!.release()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    /**
     * ８ビットのピコピコ音を生成する.
     * @param gen Generator
     * @param freq 周波数(音階)
     * @param length 音の長さ
     * @return 音データ
     */
    private fun generateSound(gen: DigitalSoundGenerator, freq: Double, length: Double): ByteArray {
        return gen.getSound(freq, length)
    }

    /**
     * 無音データを作成する
     * @param gen Generator
     * @param length 無音データの長さ
     * @return 無音データ
     */
    private fun generateEmptySound(gen: DigitalSoundGenerator, length: Double): ByteArray {
        return gen.getEmptySound(length)
    }

    override fun run() {

        // 再生中なら一旦止める
        if (audioTrack!!.playState == AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack!!.stop()
            audioTrack!!.reloadStaticData()
        }
        // 再生開始
        audioTrack!!.play()

        // スコアデータを書き込む
        for ((sound) in soundList) {
            audioTrack!!.write(sound, 0, sound.size)
        }
        // 再生停止
        audioTrack!!.stop()
    }
}
