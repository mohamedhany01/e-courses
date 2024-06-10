window.addEventListener("load", () => {
    fetch("https://api.thecatapi.com/v1/images/search")
        .then((data) => data.json())
        .then(([{ id, url, width, height }]) => {
            const html = `
                <div class="cat-img">
                    <h1>Kitten Pic</h1>
                    <img src=${url} alt=${id} width=${width} height=${height}>
                </div>
            `;

            document.body.insertAdjacentHTML("afterbegin", html);
        });
});
