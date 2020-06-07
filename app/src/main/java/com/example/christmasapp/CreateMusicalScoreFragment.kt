package com.example.christmasapp

import android.media.AudioTrack
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
    private val soundListLiveData: LiveData<ArrayList<SoundDto>> by lazy { soundListMutableLiveData }
    private val soundListMutableLiveData: MutableLiveData<ArrayList<SoundDto>> = MutableLiveData()
    private var scaleCount = 0
    private var isPlay = false
    private var isClickItem = false
    private val scaleList = mutableListOf<SoundDto>()
    private val mixSoundList = mutableListOf<SoundDto>()
    // endregion

    // region MARK: - fragment lifeCycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

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
        mixSoundList.addAll(createDummyList())

        createDummyList()
        setupRecyclerView()
        setupScaleList()
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
            val sound = scaleList[scaleCount]
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
            position = 0
        )
        // レ
        setupAdapter(
            recyclerView = binding.scaleCList,
            position = 1
        )
        // ミ
        setupAdapter(
            recyclerView = binding.scaleDList,
            position = 2
        )
        // ファ
        setupAdapter(
            recyclerView = binding.scaleEList,
            position = 3
        )
        // ソ
        setupAdapter(
            recyclerView = binding.scaleFList,
            position = 4
        )
        // ラ
        setupAdapter(
            recyclerView = binding.scaleGList,
            position = 5
        )
        // シ
        setupAdapter(
            recyclerView = binding.scaleAList,
            position = 6
        )


    }

    /**
     * 共用Adapterの設定
     *
     * @param recyclerView RecyclerView
     * @param position 音階の位置情報
     */
    private fun setupAdapter(recyclerView: RecyclerView, position: Int) {

        val scaleAdapter =
            CommonScaleAdapter().apply {
                onItemClick = {
                    isPlay = false
                    isClickItem = true
                    scaleCount = position
                    soundList[position].second[it].isSelected =
                        !soundList[position].second[it].isSelected

                    soundList[position].second[it].apply {
                        if (!this.isSelected) {
                            this.sound = generateEmptySound(soundGenerator!!, HALF_NOTE)
                        } else {
                            this.sound = scaleList[position].sound
                        }
                    }

                    soundListMutableLiveData.postValue(soundList[position].second)

                    createMixSoundList(it)

                    if (soundList[position].second[it].isSelected) {
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
     */
    private fun createDummyList(): List<SoundDto> {

        val items = arrayListOf<SoundDto>()

        for (count in 0..19) {
            items.add(
                SoundDto(
                    generateEmptySound(soundGenerator!!, HALF_NOTE),
                    HALF_NOTE
                )
            )
        }

        soundList.add(Pair(first = scaleCount, second = items))
        soundListMutableLiveData.postValue(soundList[scaleCount].second)
        return items
    }

    /**
     * 複合した音を作成する
     *
     * @param position リストの位置
     */
    private fun createMixSoundList(position: Int) {
        val items = soundGenerator!!.getMixSound(soundList, position)
        mixSoundList[position].sound = items
    }

    /**
     * LiveDataの監視
     */
    private fun observeLiveData() {
        soundListLiveData.observe(this, Observer {

            when (scaleCount) {
                0 -> (binding.scaleBList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                1 -> (binding.scaleCList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                2 -> (binding.scaleDList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                3 -> (binding.scaleEList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                4 -> (binding.scaleFList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                5 -> (binding.scaleGList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                6 -> (binding.scaleAList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
            }

            if (scaleCount < 7 && !isClickItem) {
                scaleCount++
                createDummyList()
            }
        })
    }
}
