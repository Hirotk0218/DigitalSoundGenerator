package com.example.christmasapp

import android.media.AudioTrack
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.christmasapp.databinding.FragmentCreateMusicalScoreBinding

/**
 * 楽譜作成画面
 */
class CreateMusicalScoreFragment : Fragment(), Runnable {

    companion object {
        // 音符の長さ
        const val HALF_NOTE = 0.5
    }

    // region MARK: - private field
    private lateinit var binding: FragmentCreateMusicalScoreBinding
    private var soundGenerator: DigitalSoundGenerator? = null
    private var audioTrack: AudioTrack? = null
    private var soundList = ArrayList<Pair<Int, ArrayList<SoundDto>>>()
    private var scaleCount = 0
    private var selectScalePosition = 0
    private var isPlay = false
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

        soundGenerator = DigitalSoundGenerator(44100, 44100)
        audioTrack = soundGenerator!!.audioTrack
        setupRecyclerView()
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
            for (sound in soundList[selectScalePosition].second) {
                audioTrack!!.write(sound.sound, 0, sound.sound.size)
            }
        } else {
            val sound = soundList[selectScalePosition].second.first()
            audioTrack!!.write(sound.sound, 0, sound.sound.size)
        }
        // 再生停止
        audioTrack!!.stop()
    }
    // endregion

    // region MARK: -private method
    /**
     * 各音階ごとのリストの設定
     */
    private fun setupRecyclerView() {
        // ド
        val scaleBAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_B)).apply {
                onItemClick = {
                    selectScalePosition = 0
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleBList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleBAdapter
        }

        // レ
        val scaleCAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_C)).apply {
                onItemClick = {
                    selectScalePosition = 1
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleCList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleCAdapter
        }

        // ミ
        val scaleDAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_D)).apply {
                onItemClick = {
                    selectScalePosition = 2
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleDList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleDAdapter
        }

        // ファ
        val scaleEAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_E)).apply {
                onItemClick = {
                    selectScalePosition = 3
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleEList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleEAdapter
        }

        // ソ
        val scaleFAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_F)).apply {
                onItemClick = {
                    selectScalePosition = 4
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleFList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleFAdapter
        }

        // ラ
        val scaleGAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_G)).apply {
                onItemClick = {
                    selectScalePosition = 5
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleGList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleGAdapter
        }

        // シ
        val scaleAAdapter =
            CommonScaleAdapter(dataList = createDummyList(DigitalSoundGenerator.FREQ_A)).apply {
                onItemClick = {
                    selectScalePosition = 6
                    val th = Thread(this@CreateMusicalScoreFragment)
                    th.start()
                }
            }

        binding.scaleAList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scaleAAdapter
        }
    }

    /**
     * 音階ごとのリスト生成 (20個ずつ)
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
}
