package uz.turgunboyevjurabek.musikplayer.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.turgunboyevjurabek.musikplayer.Madels.Music
import uz.turgunboyevjurabek.musikplayer.databinding.ItemRvBinding

class RvAdapter(var list: List<Music>, var rvItemClick: RvItemClick)
    : RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(music: Music, position:Int){
            itemRvBinding.txtItemArtist.text=music.author
            itemRvBinding.txtItemTitle.text=music.title

            itemRvBinding.layner.setOnClickListener {
                rvItemClick.itemClick(music, position)
            }
            itemRvBinding.root.setOnClickListener {
                rvItemClick.itemClick(music, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}
interface RvItemClick{
    fun itemClick(music: Music, position: Int)
}
