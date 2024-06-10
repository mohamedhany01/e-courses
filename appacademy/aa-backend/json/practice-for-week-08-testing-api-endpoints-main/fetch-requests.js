/*
Make fetch requests in the browser for each of the following tasks.
Paste your code for fetch requests here once you finish each task.
*/

/* =============================== Phase 1 ================================ */
/*
  Make a request with fetch request to GET /posts and print the response
  components to the console.
*/

fetch('http://localhost:5000/posts')
  .then(res => res.json());

[{ "postId": 1, "message": "Hello World!" }, { "postId": 2, "message": "Ciao!" }]



/* =============================== Phase 2 ================================ */
/*
  Make a request with fetch request to POST /posts and print the response
  components to the console.
*/

fetch('/posts', {
  method: 'POST',
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify({
    message: "Foo"
  })
})

// Your code here
[{ "postId": 3, "message": "Foo" }]
