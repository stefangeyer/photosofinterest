package at.renehollander.photosofinterest.scoreboard.entry

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.ScoreboardEntry

interface ScoreboardEntryContract {
    interface ViewHolder : BaseView {
        fun updateRank(rank: Int)
        fun updateUserImage(uri: String)
        fun updateName(name: String)
        fun updateScore(score: Int)
        fun updateIsLeader(isLeader: Boolean)
    }

    interface ViewHolderPresenter : BasePresenter<ViewHolder>

    interface Adapter : BaseView {
        fun setAll(entries: List<ScoreboardEntry>)
        fun addItem(entry: ScoreboardEntry)
        fun removeItem(entry: ScoreboardEntry)
        fun getItemAt(position: Int): ScoreboardEntry
        fun getItems(): List<ScoreboardEntry>
        fun notifyAdapter()
    }
}
