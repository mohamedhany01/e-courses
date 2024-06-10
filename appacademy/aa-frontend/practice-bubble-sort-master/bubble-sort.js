
// Iterate through the array

// If the current value is greater than its neighbor to the right
// Swap those values

// If you get to the end of the array and no swaps have occurred, return

// Otherwise, repeat from the beginning
function bubbleSort(arr) {
  for (let i = 0; i < arr.length; i++) {
    for (let j = 0; j < arr.length; j++) {
      const currentElement = arr[j];
      const newElement = arr[j + 1];

      if (currentElement > newElement) {
        let temp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = temp;
        console.log(arr.join(","));
      }
    }
  }

}

module.exports = bubbleSort;