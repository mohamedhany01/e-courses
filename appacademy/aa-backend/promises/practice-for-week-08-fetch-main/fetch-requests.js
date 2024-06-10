/*
Make fetch requests in the browser for each of the following tasks.
Paste your code for fetch requests here once you finish each task.
*/

/* =============== 1. Print the status code of the response =============== */

fetch("http://localhost:5000/products/").then(res => console.log(res.status))



/* ====== 2. Print true if the status of the response was successful ====== */

fetch("http://localhost:5000/products/").then(res => console.log(res.status === 200))



/* =================== 3. Print the Content-Type Header =================== */

fetch("http://localhost:5000/products/").then(res => console.log(res.headers.get("Content-Type")))



/* ============== 4. Print the body of the response as text =============== */

fetch("http://localhost:5000/products/").then(res => res.text()).then(text => console.log(text))
