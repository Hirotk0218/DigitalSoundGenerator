package com.example.christmasapp

import android.media.AudioTrack
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.christmasapp.databinding.FragmentCreateMusicalScoreBinding

/**
 * 楽譜作成画面
 */
class CreateMusicalScoreFragment : Fragment(), Runnable {

    companion object {
        const val HALF_NOTE = 0.5
    }

    // region MARK: - private field
    private lateinit var binding: FragmentCreateMusicalScoreBinding
    private var soundGenerator: DigitalSoundGenerator? = null
    private var audioTrack: AudioTrack? = null
    private var soundList = ArrayList<Pair<Int, ArrayList<SoundDto>>>()
    private var scaleCount = 0
    private var scalePosition = 0
    private var isPlay = false
    private val scaleList = mutableListOf<SoundDto>()
    private val mixSoundList = mutableListOf<SoundDto>()
    // endregion

    // region MARK: - fragment lifeCycle methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateMusicalScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.playButton.setOnClickListener {
            isPlay = true
            val th = Thread(this@CreateMusicalScoreFragment)
            th.start()
        }

        soundGenerator = DigitalSoundGenerator(44100, 44100)
        audioTrack = soundGenerator!!.audioTrack
        setupRecyclerView()
        setupScaleList()
        createDummyEmptyList()
    }

    override fun onDestroy() {
        super.onDestroy()

        // 再生中だったら停止してリリース
        if (audioTrack!!.playState == AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack!!.stop()
            audioTrack!!.release()
        }
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
        if (isPlay) {
            for (sound in mixSoundList) {
                audioTrack!!.write(sound.sound, 0, sound.sound.size)
            }
        } else {
            val sound = scaleList[scalePosition]
            audioTrack!!.write(sound.sound, 0, sound.sound.size)
        }
        // 再生停止
        audioTrack!!.stop()
    }
    // endregion

    // region MARK: -private methods
    /**
     * ８ビットのピコピコ音を生成する.
     * @param gen Generator
     * @param freq 周波数(音階)
     * @param length 音の長さ
     * @return 音データ
     */
    private fun generateSound(
        gen: DigitalSoundGenerator,
        freq: Double,
        length: Double
    ): ByteArray {
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

    /**
     * リスト要素用の音階リストの作成
     */
    private fun setupScaleList() {
        scaleList.clear()
        scaleList.addAll(
            listOf(
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_C,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_D,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_E,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_F,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_G,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_A,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_B,
                        HALF_NOTE
                    ), HALF_NOTE
                )
            )
        )
    }

    /**
     * 各音階ごとのAdapterの設定
     */
    private fun setupRecyclerView() {
        // ド
        setupAdapter(
            recyclerView = binding.scaleBList,
            scale = DigitalSoundGenerator.FREQ_C,
            position = 0
        )
        // レ
        setupAdapter(
            recyclerView = binding.scaleCList,
            scale = DigitalSoundGenerator.FREQ_D,
            position = 1
        )
        // ミ
        setupAdapter(
            recyclerView = binding.scaleDList,
            scale = DigitalSoundGenerator.FREQ_E,
            position = 2
        )
        // ファ
        setupAdapter(
            recyclerView = binding.scaleEList,
            scale = DigitalSoundGenerator.FREQ_F,
            position = 3
        )
        // ソ
        setupAdapter(
            recyclerView = binding.scaleFList,
            scale = DigitalSoundGenerator.FREQ_G,
            position = 4
        )
        // ラ
        setupAdapter(
            recyclerView = binding.scaleGList,
            scale = DigitalSoundGenerator.FREQ_A,
            position = 5
        )
        // シ
        setupAdapter(
            recyclerView = binding.scaleAList,
            scale = DigitalSoundGenerator.FREQ_B,
            position = 6
        )
    }

    /**
     * 共用Adapterの設定
     *
     * @param recyclerView RecyclerView
     * @param scale 音階
     * @param position 音階の位置情報
     */
    private fun setupAdapter(recyclerView: RecyclerView, scale: Double, position: Int) {
        val scaleAdapter =
            CommonScaleAdapter(dataList = createDummyList(scale)).apply {
                onItemClick = {
                    isPlay = false
                    scalePosition = position
                    soundList[scalePosition].second[it].isSelected =
                        !soundList[scalePosition].second[it].isSelected

                    soundList[scalePosition].second.forEach { soundDto ->
                        if (!soundDto.isSelected) {
                            soundDto.sound = generateEmptySound(soundGenerator!!, HALF_NOTE)
                        } else {
                            soundDto.sound = scaleList[position].sound
                        }
                    }

                    DigitalSoundGenerator.getMixSound

                    if (soundList[scalePosition].second[it].isSelected) {
                        val th = Thread(this@CreateMusicalScoreFragment)
                        th.start()
                    }
                }
            }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleAdapter
        }
    }

    /**
     * 音階ごとのリスト生成 (20個ずつ)
     *
     * @param scale 音階
     */
    private fun createDummyList(scale: Double): List<SoundDto> {

        val items = arrayListOf<SoundDto>()

        for (count in 0..19) {
            items.add(
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        scale,
                        HALF_NOTE
                    ), HALF_NOTE
                )
            )
        }

        soundList.add(Pair(first = scaleCount, second = items))
        scaleCount++
        return items
    }

    /**
     * 休符リスト生成 (20個)
     */
    private fun createDummyEmptyList() {

        val items = arrayListOf<SoundDto>()

        for (count in 0..19) {
            items.add(
                SoundDto(
                    generateEmptySound(soundGenerator!!, HALF_NOTE),
                    HALF_NOTE
                )
            )
        }

        mixSoundList.addAll(items)
    }
}
