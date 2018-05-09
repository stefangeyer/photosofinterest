package at.renehollander.photosofinterest.scoreboard.entry

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.ScoreboardEntry

class ScoreboardEntryAdapter(
) : RecyclerView.Adapter<ScoreboardEntryViewHolder>(), ScoreboardEntryContract.Adapter {

    private var scores = mutableListOf<ScoreboardEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardEntryViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.scoreboard_item, parent, false)
        return ScoreboardEntryViewHolder(item, this)
    }

    override fun getItemCount(): Int = this.scores.size

    override fun onBindViewHolder(holder: ScoreboardEntryViewHolder, position: Int) {
        val entry = getItemAt(position)

        holder.updateRank(position)
        holder.updateUserImage(entry.user.image.uri)
        holder.updateName(entry.user.name)
        holder.updateScore(entry.score)
    }

    override fun setAll(entries: List<ScoreboardEntry>) {
        this.scores.clear()
        this.scores.addAll(entries)
        notifyAdapter()
    }

    override fun addItem(entry: ScoreboardEntry) {
        this.scores.add(entry)
        notifyAdapter()
    }

    override fun removeItem(entry: ScoreboardEntry) {
        this.scores.remove(entry)
        notifyAdapter()
    }

    override fun getItemAt(position: Int): ScoreboardEntry = this.scores[position]

    override fun getItems(): List<ScoreboardEntry> = this.scores

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

}
