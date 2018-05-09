package at.renehollander.photosofinterest.challenges.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Challenge

class ChallengeOverviewAdapter(var sc: ChallengeOverviewContract.View.OnShowDetailsListener) : RecyclerView.Adapter<ChallengeOverviewViewHolder>(), ChallengeOverviewContract.Adapter {

    override fun showChallenge(challenge: Challenge) {
        sc.showDetails(challenge)
    }

    private val challenges = mutableListOf<Challenge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeOverviewViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_challenge_overview, parent, false)
        return ChallengeOverviewViewHolder(item, this)
    }

    override fun getItemCount(): Int = this.challenges.size

    override fun onBindViewHolder(holder: ChallengeOverviewViewHolder, position: Int) {
        holder.presenter.changePosition(position)
        holder.presenter.bind()
    }

    override fun setAll(posts: List<Challenge>) {
        this.challenges.clear()
        this.challenges.addAll(posts)
        notifyAdapter()
    }

    override fun addItem(post: Challenge) {
        this.challenges.add(post)
        notifyAdapter()
    }

    override fun removeItem(post: Challenge) {
        this.challenges.remove(post)
        notifyAdapter()
    }

    override fun getItemAt(position: Int): Challenge = this.challenges[position]

    override fun getItems(): List<Challenge> = this.challenges

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }
}
