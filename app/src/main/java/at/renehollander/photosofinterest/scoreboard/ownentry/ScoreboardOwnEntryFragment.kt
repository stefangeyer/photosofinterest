package at.renehollander.photosofinterest.scoreboard.ownentry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.scoreboard_item.*
import javax.inject.Inject

class ScoreboardOwnEntryFragment @Inject constructor() : DaggerFragment(), ScoreboardOwnEntryContract.View {

    @Inject
    lateinit var presenter: ScoreboardOwnEntryContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.scoreboard_item, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateIsLeader(isLeader: Boolean) {
        if (isLeader) {
            scoreboard_leader_image.setImageResource(R.drawable.crown)
            layout.setBackgroundResource(R.color.colorLeader)
        } else {
            scoreboard_leader_image.setImageBitmap(null)
            layout.setBackgroundResource(R.color.colorAccent)
        }
    }

    override fun updateRank(rank: Int) {
        this.scoreboard_item_rank.text = rank.toString()
    }

    override fun updateUserImage(uri: String) {
        this.scoreboard_item_profileimage.setImageURI(uri)
    }

    override fun updateName(name: String) {
        this.scoreboard_item_username.text = name
    }

    override fun updateScore(score: Int) {
        this.scoreboard_item_points.text = context!!.getString(R.string.scoreboard_points, score);
    }

}
