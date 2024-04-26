// T => O(N)
// S => O(1)
const findMinimum = arr => {

  if (arr.length === 0) {
    return undefined;
  }

  let min = Infinity;

  for (const n of arr) {
    if (n < min) {
      min = n;
    }
  }

  return min;

};

// T => O(N)
// S => O(N)
const runningSum = arr => {
  if (arr.length === 0) {
    return [];
  }

  const result = [];
  let prev = 0;

  for (const n of arr) {
    prev = n + prev;

    result.push(prev);
  }

  return result;
};

// T => O(N)
// S => O(1)
const evenNumOfChars = arr => {
  let result = 0;

  for (const str of arr) {

    if (str.length % 2 === 0) {
      result++;
    }
  }

  return result;
};

// T => O(N^2)
// S => O(N)
const smallerThanCurr = arr => {

  const result = [];

  for (let i = 0; i < arr.length; i++) {
    const current = arr[i];

    let counter = 0;
    for (let j = 0; j < arr.length; j++) {
      const next = arr[j];

      if (next < current) {
        counter++;
      }

    }

    result.push(counter);
  }

  return result;

};

// T => O(N^2)
// S => O(1)
const twoSum = (arr, target) => {
  for (let i = 0; i < arr.length; i++) {
    const current = arr[i];

    for (let j = i + 1; j < arr.length - 1; j++) {
      const next = arr[j];

      if (current + next === target) {
        return true;
      }
    }
  }

  return false;
};

// T => O(N)
// S => O(1)
const secondLargest = arr => {
  if (arr.length < 2) {
    return undefined;
  }

  if (arr.length === 2) {
    return Math.min(arr[0], arr[1]);
  }

  let max = -Infinity;
  let second = -Infinity;

  for (const n of arr) {
    if (n > max) {
      second = max;
      max = n;
    } else if (n > second && n !== max) {
      second = n;
    }
  }

  return second;
};

// T => O(N)
// S => O(N)
const shuffle = (arr) => {
  const result = [...arr];

  for (let i = 0; i < arr.length; i++) {

    const j = Math.floor(Math.random() * (i + 1));

    // Fisher-Yates algorithm
    [result[i], result[j]] = [result[j], result[i]];
  }

  return result;
};


module.exports = [findMinimum, runningSum, evenNumOfChars, smallerThanCurr, twoSum, secondLargest, shuffle];