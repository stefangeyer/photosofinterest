package at.renehollander.photosofinterest.data

import at.renehollander.photosofinterest.data.source.GetRecordCallback

class UpvoteEvent(val post: Post, val callback: GetRecordCallback<Post>, val alreadyVoted: GetRecordCallback<Boolean>)

class DownvoteEvent(val post: Post, val callback: GetRecordCallback<Post>, val alreadyVoted: GetRecordCallback<Boolean>)
