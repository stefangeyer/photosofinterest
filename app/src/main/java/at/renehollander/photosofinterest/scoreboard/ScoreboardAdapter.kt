package at.renehollander.photosofinterest.scoreboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import at.renehollander.photosofinterest.R
import de.hdodenhof.circleimageview.CircleImageView

class ScoreboardAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<ScoreboardAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val rank: TextView
        val username: TextView
        val profileImage: CircleImageView
        val points: TextView

        init {
            rank = v.findViewById(R.id.scoreboard_item_rank)
            username = v.findViewById(R.id.scoreboard_item_username)
            profileImage = v.findViewById(R.id.scoreboard_item_profileimage)
            points = v.findViewById(R.id.scoreboard_item_points)
        }

        fun set(rank: Int, username: String, points: Int) {
            this.rank.text = rank.toString()
            this.username.text = username
            this.points.text = "$points points"
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.scoreboard_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.set(position, dataSet[position], position * 10)
    }

    override fun getItemCount() = dataSet.size

}
