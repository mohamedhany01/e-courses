export const scoreUtils = (function () {
    return {
        increment: function () {
            const score = document.getElementById("score");
            let scoreNum = parseInt(score.innerText);
            return ++scoreNum;
        },
        decrement: function () {
            const score = document.getElementById("score");
            let scoreNum = parseInt(score.innerText);
            return --scoreNum;
        },
        update: function (value) {
            const score = document.getElementById("score");
            score.innerText = value;
        },
        reset: function () {
            const score = document.getElementById("score");
            score.innerText = 0;
        },
        getScore: function () {
            const score = document.getElementById("score");
            return parseInt(score.innerText);
        }
    }
})();

export const catUtils = (function () {
    return {
        voteUP: function ({ value, cb }) {
            cb(value);
        },
        voteDown: function ({ value, cb }) {
            cb(value);
        }
    }
})();

export const appUtils = (function () {
    let currentImag = "";
    let currentVote = 0;
    let comments = [];

    return {
        setImage: function (img) {
            currentImag = img;
        },
        getImage: function () {
            return currentImag;
        },
        setVote: function (v) {
            currentVote = v;
        },
        getVote: function () {
            return currentVote;
        },
        addComment: function (comment) {
            comments.push(comment);
        },
        getComments: function () {
            return comments;
        },
        resetComments: function () {
            comments = [];
        }
    }
})()
