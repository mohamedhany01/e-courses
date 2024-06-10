const STORAGE_KEY = {
    image: "image",
    vote: "vote",
    comments: "comments"
}

const storeItem = {
    image: function (url) {
        localStorage.setItem(STORAGE_KEY.image, url);
    },
    vote: function (value) {
        localStorage.setItem(STORAGE_KEY.vote, value);
    },
    comments: function (comment, reset = false) {

        if (reset) {
            localStorage.setItem(STORAGE_KEY.comments, JSON.stringify([]));

            return;
        }

        const currentComments = loadItem.comments();

        if (currentComments) {

            if (Array.isArray(comment)) {
                comment.forEach(element => {
                    currentComments.push(element);
                });
            } else {
                currentComments.push(comment);
            }

            const commentObj = JSON.stringify(currentComments);

            localStorage.setItem(STORAGE_KEY.comments, commentObj);
        }
    }
}

const loadItem = {
    image: function () {
        const currentURL = localStorage.getItem(STORAGE_KEY.image);

        if (currentURL) {
            return currentURL;
        }

        return null;
    },
    vote: function () {
        const currentValue = localStorage.getItem(STORAGE_KEY.vote);

        if (currentValue) {
            return currentValue;
        }

        return null;
    },
    comments: function () {
        const storedComments = localStorage.getItem(STORAGE_KEY.comments);

        if (storedComments) {
            return JSON.parse(storedComments);
        }

        return null;
    }
}

const extra = {
    resetImage: function () {
        storeItem.image("");
    },
    resetScore: function () {
        storeItem.vote(0);
    },
    resetComments: function () {
        storeItem.comments([], true);
    },
}

export const storageManager = (function () {
    return {
        storeItem,
        loadItem,
        extra
    }
})();
