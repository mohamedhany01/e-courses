function stretch() {
  return new Promise(function (resolve, reject) {
    setTimeout(() => {
      resolve("done stretching");
    }, 1000);
  });
}

function runOnTreadmill() {
  return new Promise(function (resolve, reject) {
    setTimeout(() => {
      resolve("done running on treadmill");
    }, 500);
  });
}

function liftWeights() {
  return new Promise(function (resolve, reject) {
    setTimeout(() => {
      resolve("done lifting weights");
    }, 20000);
  });
}

function workout() {
  stretch().then((msg) => {
    console.log(msg);

    runOnTreadmill().then((msg) => {
      console.log(msg);

      liftWeights().then((msg) => {
        console.log(msg);

        console.log("done working out");
      });
    });
  });
}


/* ============================ TEST YOUR CODE ============================

Run the file (`node phase-1.js`) and check your output against the expected
output.
*/


workout();
// should print out the following:
// done stretching
// done running on treadmill
// done lifting weights
// done working out