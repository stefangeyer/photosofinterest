package at.renehollander.photosofinterest.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.renehollander.photosofinterest.R

class ScoreboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScoreboardFragment()
    }
}
