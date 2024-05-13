function stretch(timeLeft) {
  const TIME = 1000;

  return new Promise(function (resolve, reject) {
    if (timeLeft < TIME) return reject("Error:  you don't have enough time to run on stretch");

    setTimeout(() => {
      resolve({ msg: "done stretching", left: timeLeft - TIME });
    }, TIME);
  });
}


function runOnTreadmill(timeLeft) {
  const TIME = 500;

  return new Promise(function (resolve, reject) {
    if (timeLeft < TIME) return reject("Error:  you don't have enough time to run on treadmill");

    setTimeout(() => {
      resolve({ msg: "done running on treadmill", left: timeLeft - TIME });
    }, TIME);
  });
}


function liftWeights(timeLeft) {
  const TIME = 2000;

  return new Promise(function (resolve, reject) {
    if (timeLeft < TIME) return reject("Error:  you don't have enough time to lift weights");

    setTimeout(() => {
      resolve({ msg: "done lifting weights", left: timeLeft - TIME });
    }, TIME);
  });
}


function workout(totalTime) {
  stretch(totalTime).then(({ msg, left }) => {
    console.log(msg);

    runOnTreadmill(left).then(({ msg, left }) => {
      console.log(msg);

      liftWeights(left).then(({ msg, left }) => {
        console.log(msg);

        console.log(`done working out with ${left} seconds left`);
      }).catch((err) => console.log(err));

    }).catch((err) => console.log(err));;

  }).catch((err) => console.log(err));
}

/* ============================ TEST YOUR CODE ============================

Comment in each invocation of your workout function below and run the file
(node phase-2.js) to see if you get the expected output.
*/


workout(500);
// should print out the following:
// Error:  you dont have enough time to stretch


workout(1000);
// should print out the following:
// done stretching
// Error:  you dont have enough time to run on treadmill


workout(2000);
// should print out the following:
// done stretching
// done running on treadmill
// Error:  you dont have enough time to lift weights


workout(4000);
// should print out the following:
//   done stretching
//   done running on treadmill
//   done lifting weights
//   done working out with 0.5 seconds left