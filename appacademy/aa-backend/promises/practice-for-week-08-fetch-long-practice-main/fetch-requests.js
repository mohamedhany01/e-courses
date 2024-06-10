/*
Make fetch requests in the browser for each of the following phases.
Paste your code for fetch requests here once you finish each phase.
*/

/* ============================== Phase 1 ============================== */

fetch("http://localhost:5000/products/", {
    method: "POST",
    body: "name=Buz&description=Made+by+Manatee+Coffee&price=11%2E99&categories=grocery",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    }
}).then(response => response).then(data => console.log(data));



/* ============================== Phase 2 ============================== */

fetch("http://localhost:5000/products/", {
    method: "POST",
    body: "name=Buz&description=Made+by+Manatee+Coffee&price=11%2E99&categories=grocery",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    }
}).then(response => response);



/* ============================== Phase 3 ============================== */

fetch("http://localhost:5000/products/", {
    method: "POST",
    body: new URLSearchParams({
        name: "Caribbean Delight Coffee",
        description: "Made by Manatee Coffee",
        price: 11.99,
        categories: "grocery"
    }),
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    }
}).then(response => response).then(data => console.log(data));
