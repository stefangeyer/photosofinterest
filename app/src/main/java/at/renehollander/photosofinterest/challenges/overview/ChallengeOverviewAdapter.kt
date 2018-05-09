package at.renehollander.photosofinterest.challenges.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.data.Challenge

class ChallengeOverviewAdapter(var sc: ChallengeOverviewContract.View.OnShowDetailsListener) :
        RecyclerView.Adapter<ChallengeOverviewViewHolder>(), ChallengeOverviewContract.Adapter {

    override val itemList = mutableListOf<Challenge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeOverviewViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_challenge_overview, parent, false)
        return ChallengeOverviewViewHolder(item, this)
    }

    override fun getItemCount(): Int = this.itemList.size

    override fun onBindViewHolder(holder: ChallengeOverviewViewHolder, position: Int) {
        holder.presenter.changePosition(position)
        holder.presenter.bind()
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun showChallenge(challenge: Challenge) {
        sc.showDetails(challenge)
    }
}
