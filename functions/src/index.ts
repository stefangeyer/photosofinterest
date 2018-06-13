import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp(functions.config().firebase);

function getGlobalScoreboardImpl() {
    return admin.firestore().collection('challenges').get().then(snapshot => {
        return Promise.all(snapshot.docs.map(challenge => {
            return challenge.ref.collection("posts").get().then((posts) => {
                return Promise.all(posts.docs.map(post => {
                    return {
                        user: post.data()["user"].id,
                        upvotes: post.data()["upvotes"],
                        downvotes: post.data()["downvotes"]
                    }
                }))
            })
        }))
    }).then(values => {
        let map = {};
        [].concat.apply([], values).forEach(item => {
            if (!map[item.user]) map[item.user] = 0;
            map[item.user] += item.upvotes - item.downvotes;
        });
        return map;
    }).then(scores => {
        return Promise.all(Object.keys(scores).map(userid => {
            return admin.firestore().collection('users').doc(userid).get().then(userData => {
                return {
                    user: {
                        id: userData.id,
                        name: userData.data()["name"],
                        email: userData.data()["email"],
                        image: userData.data()["image"]
                    },
                    score: scores[userid]
                }
            })
        }));
    }).then(result => {
        return result.sort((a, b) => b.score - a.score);
    })
}

function upvoteImpl(user, challenge, post) {
    return admin.firestore().collection("challenges").doc(challenge).collection("posts").doc(post).get().then(postData => {
        return admin.firestore().collection("challenges").doc(challenge).collection("posts").doc(post).update({
            upvotes: postData.data()["upvotes"] + 1
        }).then(d => {
            return {
                upvotes: postData.data()["upvotes"] + 1,
                downvotes: postData.data()["downvotes"],
            }
        })
    });
}

function downvoteImpl(user, challenge, post) {
    return admin.firestore().collection("challenges").doc(challenge).collection("posts").doc(post).get().then(postData => {
        return admin.firestore().collection("challenges").doc(challenge).collection("posts").doc(post).update({
            downvotes: postData.data()["downvotes"] + 1
        }).then(d => {
            return {
                upvotes: postData.data()["upvotes"],
                downvotes: postData.data()["downvotes"] + 1,
            }
        })
    });
}

export const getGlobalScoreboardDebug = functions.https.onRequest((req, res) => {
    getGlobalScoreboardImpl().then(d => {
        res.send(d)
    }).catch(e => {
        res.send({});
        console.error(e)
    })
});

export const getGlobalScoreboard = functions.https.onCall((data, context) => {
    return getGlobalScoreboardImpl();
});

export const upvoteDebug = functions.https.onRequest((req, res) => {
    upvoteImpl("MentJDknd5SeJkixnFbrzSvSync2", "BK99EjGsYIyGK44KqJ3V", "pqq7TuQ1qHLfLTRkdrNI").then(d => {
        res.send(d)
    }).catch(e => {
        res.send({});
        console.error(e)
    })
});

export const upvote = functions.https.onCall((data, context) => {
    return upvoteImpl(context.auth.uid, data.challenge, data.post)
});

export const downvoteDebug = functions.https.onRequest((req, res) => {
    downvoteImpl("MentJDknd5SeJkixnFbrzSvSync2", "BK99EjGsYIyGK44KqJ3V", "pqq7TuQ1qHLfLTRkdrNI").then(d => {
        res.send(d)
    }).catch(e => {
        res.send({});
        console.error(e)
    })
});

export const downvote = functions.https.onCall((data, context) => {
    return downvoteImpl(context.auth.uid, data.challenge, data.post)
});
