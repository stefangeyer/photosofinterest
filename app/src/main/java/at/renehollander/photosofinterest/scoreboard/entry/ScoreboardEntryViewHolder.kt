package at.renehollander.photosofinterest.scoreboard.entry

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import at.renehollander.photosofinterest.R
import com.facebook.drawee.view.SimpleDraweeView

class ScoreboardEntryViewHolder(
        private val parentView: View,
        private val adapter: ScoreboardEntryContract.Adapter
) : RecyclerView.ViewHolder(parentView), ScoreboardEntryContract.ViewHolder {

    val presenter = ScoreboardEntryViewHolderPresenter(this.adapter)

    init {
        this.presenter.takeView(this)
    }

    override fun updateIsLeader(isLeader: Boolean) {
        if (isLeader) {
            leaderImage.setImageResource(R.drawable.crown)
            layout.setBackgroundResource(R.color.colorLeader)
        } else {
            leaderImage.setImageBitmap(null)
            layout.setBackgroundResource(R.color.colorWhiteTransparent)
        }
    }

    override fun updateRank(rank: Int) {
        this.rank.text = rank.toString()
    }

    override fun updateUserImage(uri: String) {
        this.profileImage.setImageURI(uri)
    }

    override fun updateName(name: String) {
        this.username.text = name
    }

    override fun updateScore(score: Int) {
        this.points.text = parentView.context.resources.getQuantityString(R.plurals.scoreboard_points, score, score)
    }

    private val rank: TextView = parentView.findViewById(R.id.scoreboard_item_rank)
    private val username: TextView = parentView.findViewById(R.id.scoreboard_item_username)
    private val profileImage: SimpleDraweeView = parentView.findViewById(R.id.scoreboard_item_profileimage)
    private val points: TextView = parentView.findViewById(R.id.scoreboard_item_points)
    private val leaderImage: ImageView = parentView.findViewById(R.id.scoreboard_leader_image)
    private val layout: LinearLayout = parentView.findViewById(R.id.layout)

}