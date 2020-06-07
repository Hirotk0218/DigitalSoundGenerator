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
                        DigitalSoundGenerator.FREQ_Df,
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
                        DigitalSoundGenerator.FREQ_Ef,
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
                        DigitalSoundGenerator.FREQ_Gf,
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
                        DigitalSoundGenerator.FREQ_Af,
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
                        DigitalSoundGenerator.FREQ_Hf,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.FREQ_H,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_C,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_Df,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_D,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_Ef,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_E,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_F,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_Gf,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_G,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_Af,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_A,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_Hf,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.OCT_FREQ_H,
                        HALF_NOTE
                    ), HALF_NOTE
                ),
                SoundDto(
                    generateSound(
                        soundGenerator!!,
                        DigitalSoundGenerator.SEC_OCT_FREQ_C,
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
        // C
        setupAdapter(
            recyclerView = binding.scaleCList,
            position = 0
        )
        // Db
        setupAdapter(
            recyclerView = binding.scaleDbList,
            position = 1
        )
        // D
        setupAdapter(
            recyclerView = binding.scaleDList,
            position = 2
        )
        // Eb
        setupAdapter(
            recyclerView = binding.scaleEbList,
            position = 3
        )
        // E
        setupAdapter(
            recyclerView = binding.scaleEList,
            position = 4
        )
        // F
        setupAdapter(
            recyclerView = binding.scaleFList,
            position = 5
        )
        // Gb
        setupAdapter(
            recyclerView = binding.scaleGbList,
            position = 6
        )
        // G
        setupAdapter(
            recyclerView = binding.scaleGList,
            position = 7
        )
        // Ab
        setupAdapter(
            recyclerView = binding.scaleAbList,
            position = 8
        )
        // A
        setupAdapter(
            recyclerView = binding.scaleAList,
            position = 9
        )
        // Hb
        setupAdapter(
            recyclerView = binding.scaleHbList,
            position = 10
        )
        // H
        setupAdapter(
            recyclerView = binding.scaleHList,
            position = 11
        )
        // 1オクターブのC
        setupAdapter(
            recyclerView = binding.octScaleCList,
            position = 12
        )
        // 1オクターブのDb
        setupAdapter(
            recyclerView = binding.octScaleDbList,
            position = 13
        )
        // 1オクターブのD
        setupAdapter(
            recyclerView = binding.octScaleDList,
            position = 14
        )
        // 1オクターブのEb
        setupAdapter(
            recyclerView = binding.octScaleEbList,
            position = 15
        )
        // 1オクターブのE
        setupAdapter(
            recyclerView = binding.octScaleEList,
            position = 16
        )
        // 1オクターブのF
        setupAdapter(
            recyclerView = binding.octScaleFList,
            position = 17
        )
        // 1オクターブのG
        setupAdapter(
            recyclerView = binding.octScaleGbList,
            position = 18
        )
        // 1オクターブのG
        setupAdapter(
            recyclerView = binding.octScaleGList,
            position = 19
        )
        // 1オクターブのA
        setupAdapter(
            recyclerView = binding.octScaleAbList,
            position = 20
        )
        // 1オクターブのA
        setupAdapter(
            recyclerView = binding.octScaleAList,
            position = 21
        )
        // 1オクターブのHb
        setupAdapter(
            recyclerView = binding.octScaleHbList,
            position = 22
        )
        // 1オクターブのH
        setupAdapter(
            recyclerView = binding.octScaleHList,
            position = 23
        )
        // 2オクターブのC
        setupAdapter(
            recyclerView = binding.secOctScaleCList,
            position = 24
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
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = scaleAdapter
        }
    }

    /**
     * 音階ごとのリスト生成 (40個ずつ)
     */
    private fun createDummyList(): List<SoundDto> {

        val items = arrayListOf<SoundDto>()

        for (count in 0..39) {
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
                0 -> (binding.scaleCList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                1 -> (binding.scaleDbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                2 -> (binding.scaleDList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                3 -> (binding.scaleEbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                4 -> (binding.scaleEList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                5 -> (binding.scaleFList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                6 -> (binding.scaleGbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                7 -> (binding.scaleGList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                8 -> (binding.scaleAbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                9 -> (binding.scaleAList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                10 -> (binding.scaleHbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                11 -> (binding.scaleHList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                12 -> (binding.octScaleCList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                13 -> (binding.octScaleDbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                14 -> (binding.octScaleDList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                15 -> (binding.octScaleEbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                16 -> (binding.octScaleEList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                17 -> (binding.octScaleFList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                18 -> (binding.octScaleGbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                19 -> (binding.octScaleGList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                20 -> (binding.octScaleAbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                21 -> (binding.octScaleAList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                22 -> (binding.octScaleHbList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                23 -> (binding.octScaleHList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
                24 -> (binding.secOctScaleCList.adapter as? CommonScaleAdapter)
                    ?.submitList(it)
            }

            if (scaleCount < 25 && !isClickItem) {
                scaleCount++
                createDummyList()
            }
        })
    }
}
