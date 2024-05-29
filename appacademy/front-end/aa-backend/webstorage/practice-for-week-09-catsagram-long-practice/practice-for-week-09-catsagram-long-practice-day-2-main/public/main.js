import { scoreUtils, catUtils, appUtils } from "./utils.js";

export const createMainContent = () => {
    // Create h1
    const h1 = document.createElement("h1");
    h1.innerText = "Catstagram";

    // Create img
    const img = document.createElement("img");
    img.style.margin = "20px";
    img.style.maxWidth = "750px";

    const container = document.querySelector(".container");
    container.appendChild(h1);
    container.appendChild(img);

    // Next image button
    const nextImageButton = `
        <div id="next-button-wrapper">
            <button id="next-image">Next Image</button>
        </div>
    `;

    container.insertAdjacentHTML("beforeend", nextImageButton);

    // Create score
    const scoreHTML = `
        <div id="voting">
            <p>Popularity Score: <span id="score">0</p>
            <div class="buttons">
                <button id="upvote">Upvote</button>
                <button id="downvote">Downvote</button>
            </div>
        </div>
    `;

    container.insertAdjacentHTML("beforeend", scoreHTML);

    // Create comment
    const commentHTML = `
        <div id="comment">
            <label>
                Comment:
                <input type="text" id="input-comment" placeholder="Add a comment..." />
            </label>
            <button id="add-comment">Submit</button>
        </div>
    `;

    container.insertAdjacentHTML("beforeend", commentHTML);

    // Create comments section
    const commentsSectionHTML = `
        <div id="comments">
        </div>
    `;

    container.insertAdjacentHTML("beforeend", commentsSectionHTML);

    const nextButton = document.querySelector("#next-image");
    nextButton.addEventListener("click", () => {
        // const nextButtonWrapper = document.querySelector("#next-button-wrapper");
        // nextButtonWrapper.style.display = "none";
        fetchImage();
    });

    fetchImage();
};

export const setVote = () => {

    const upvote = document.getElementById("upvote");

    const downvote = document.getElementById("downvote");

    upvote.addEventListener("click", () => {
        catUtils.voteUP({
            value: scoreUtils.increment(),
            cb: scoreUtils.update,
        });

        appUtils.getVote(scoreUtils.getScore());
    });

    downvote.addEventListener("click", () => {
        catUtils.voteDown({
            value: scoreUtils.decrement(),
            cb: scoreUtils.update,
        });

        appUtils.getVote(scoreUtils.getScore());
    });
}

const updateCommentsSection = () => {
    const comments = document.getElementById("comments");

    comments.innerHTML = "";

    appUtils.getComments().forEach(comment => {
        const newComment = document.createElement("div");
        newComment.setAttribute("class", "comment");

        newComment.innerText = comment;

        comments.insertAdjacentElement("beforeend", newComment);
    });
}

export const setComments = () => {

    const addComment = document.getElementById("add-comment");

    addComment.addEventListener("click", () => {
        const inputComment = document.getElementById("input-comment");
        appUtils.addComment(inputComment.value);

        inputComment.value = "";

        updateCommentsSection();
    });

}

const fetchImage = async () => {
    // Fetch image from API and set img url
    try {
        const kittenResponse = await fetch("https://api.thecatapi.com/v1/images/search?size=small");
        // Converts to JSON
        const kittenData = await kittenResponse.json();
        // console.log(kittenData);
        const kittenImg = document.querySelector("img");

        kittenImg.src = kittenData[0].url;
        appUtils.setImage(kittenData[0].url);

        kittenImg.setAttribute("id", kittenData[0].id)

        scoreUtils.reset();

        appUtils.setVote(scoreUtils.getScore());

        appUtils.resetComments();

        updateCommentsSection();

        // setTimeout(() => {
        //     const nextButtonWrapper = document.querySelector("#next-button-wrapper");
        //     nextButtonWrapper.style.display = "block";
        // }, 1000);

    } catch (e) {
        console.log("Failed to fetch image", e);
    }
};
