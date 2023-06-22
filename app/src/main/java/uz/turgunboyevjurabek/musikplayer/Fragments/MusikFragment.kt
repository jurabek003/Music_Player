package uz.turgunboyevjurabek.musikplayer.Fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import uz.turgunboyevjurabek.musikplayer.Madels.Music
import uz.turgunboyevjurabek.musikplayer.R
import uz.turgunboyevjurabek.musikplayer.databinding.FragmentMusikBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MusikFragment : Fragment() {
    private val binding by lazy { FragmentMusikBinding.inflate(layoutInflater) }
    private var param1: String? = null
    private var param2: String? = null
    var mediaPlayer:MediaPlayer? = null
    private var position:Int=0
    private lateinit var  music: Music
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        position= arguments?.getInt("position")!!
        music=arguments?.getSerializable("music") as Music

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
              onDetach()
        }
        resumeMusic()

        return binding.root
    }

    private fun resumeMusic() {
        if (position!=-1){
            mediaPlayer?.release() // Eski MediaPlayer obyektini tozalash
            val music = MyData.list[position]

            mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(music.musicPath))
            mediaPlayer?.start()
            if (!mediaPlayer?.isPlaying!! ){
                mediaPlayer?.start()
            }
            binding.myPlay.setOnClickListener {
                if (mediaPlayer?.isPlaying!!){
                    mediaPlayer?.pause()
                    binding.musikImage2.visibility=View.VISIBLE
                    binding.musikImage.visibility=View.GONE
                    binding.myPlay.setImageResource(R.drawable.ic_stop)
                }else{
                    mediaPlayer?.start()
                    binding.musikImage.visibility=View.VISIBLE
                    binding.musikImage2.visibility=View.GONE
                    binding.myPlay.setImageResource(R.drawable.ic_play)
                }
            }
//            if (MyData.list[position].imagePath!=""){
//                val bm=BitmapFactory.decodeFile(MyData.list[position].imagePath)
//                binding.musikImage.setImageBitmap(bm)
//            } rasm quyish uchun chala


            // oldi musiqani ijro ettirish
            binding.musikNext.setOnClickListener {
                if (++position<MyData.list.size){
                    releseMP()
                    binding.musikImage.visibility=View.VISIBLE
                    binding.musikImage2.visibility=View.GONE
                    resumeMusic()
                    binding.myPlay.setImageResource(R.drawable.ic_play)
                }else{
                    position=0
                    releseMP()
                    binding.musikImage.visibility=View.VISIBLE
                    binding.musikImage2.visibility=View.GONE
                    resumeMusic()
                    binding.myPlay.setImageResource(R.drawable.ic_play)
                }
            }
            // orqa misiqani ijri ettirish
            binding.musikBofore.setOnClickListener {
                if (--position >= 0){
                    releseMP()
                    binding.musikImage.visibility=View.VISIBLE
                    binding.musikImage2.visibility=View.GONE
                    resumeMusic()
                    binding.myPlay.setImageResource(R.drawable.ic_play)
                }else{
                    position=MyData.list.size-1
                    releseMP()
                    binding.musikImage.visibility=View.VISIBLE
                    binding.musikImage2.visibility=View.GONE
                    resumeMusic()
                    binding.myPlay.setImageResource(R.drawable.ic_play)
                }
            }

            handler= Handler(activity?.mainLooper!!)
            binding.mySikbar.max=mediaPlayer?.duration!!
            binding.musicTitle.text=MyData.list[position].title
            binding.musicArtist.text=MyData.list[position].author
            binding.musicEndText.text=milliSecondtoTimer(mediaPlayer?.duration!!.toLong())

            if (mediaPlayer?.isPlaying!!){
                handler.postDelayed(runnabel,1000)
            }

            // SeekBarni tanlangan bazitsiyasini ijro etishi
            binding.mySikbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                }
                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    mediaPlayer?.seekTo(p0?.progress!!)
                }
            })
        }

    }//

    private fun releseMP(){
        if(mediaPlayer != null){
            try {
                mediaPlayer?.release()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        releseMP()
        //handler.removeCallbacks(runnabel)
    }

    private val runnabel=object:Runnable{
        override fun run() {
            if (mediaPlayer != null){
                try {
                    binding.mySikbar.progress=mediaPlayer?.currentPosition!!
                    binding.musicStartText.text=milliSecondtoTimer(mediaPlayer?.currentPosition!!.toLong())
                    if (binding.musicStartText.text.toString()==binding.musicEndText.text.toString()){
                        releseMP()
                        if (++position < MyData.list.size){
                            releseMP()
                            binding.musikImage.visibility=View.VISIBLE
                            binding.musikImage2.visibility=View.GONE
                            resumeMusic()
                        }else{
                            position=0
                            releseMP()
                            binding.musikImage.visibility=View.VISIBLE
                            binding.musikImage2.visibility=View.GONE
                            resumeMusic()
                        }

                    }
                    handler.postDelayed(this,100)
                }catch (e:IllegalStateException){
                    releseMP()
                }
            }

        }


    }

    // musiqani qaysi pasitsiyada ekanligini aniqlash uchun qo'yilgan textlarni ishlatish
    private  fun milliSecondtoTimer(milliSeconds:Long):String?{
        var finalTimeString=""

        val second=milliSeconds/1000
        val hours=second/3600
        val secQ=second % 3600
        val minuts=secQ / 60
        val Lsec=secQ % 60


        finalTimeString+=String.format("%02d:",hours )
        finalTimeString+=String.format("%02d:",minuts)
        finalTimeString+=String.format("%02d", Lsec)

        return finalTimeString
    }

}