package at.renehollander.photosofinterest.data

data class Scoreboard(var title: String, var scores: List<ScoreboardEntry>, var challenge: Challenge)

data class ScoreboardEntry(var post: Post?, var user: User, var score: Int)
