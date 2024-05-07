

function selectionSort(arr) {

  // Copy the original array

  // Create an array to store the sorted values

  // While the array is not empty...

  // Find the index of the minimum value in the unsorted half

  // Save and remove the value at the min index

  // Add the min value to the end of the sorted array

  const tempArray = [...arr];
  const sorted = [];
  console.log("");
  while (tempArray.length - 1 >= 0) {
    let minIndex = 0;
    for (let i = 1; i < tempArray.length; i++) {
      if (tempArray[i] < tempArray[minIndex]) {
        minIndex = i;
      }
    }

    sorted.push(tempArray[minIndex]);
    tempArray.splice(minIndex, 1);
    console.log(sorted.join(","));
  }
  return sorted;
}



function selectionSortInPlace(arr) {

  // Set a pointer at zero diving the array into sorted and unsorted halves

  // Repeat while the unsorted half is not empty:

  // Find the index of the minimum value in the unsorted half

  // Save the min value

  // Shift every unsorted value to the left of the min value to the right by 1

  // Put the min value at the divider

  // Increment the divider and repeat

  const n = arr.length;

  for (let i = 0; i < n - 1; i++) {
    console.log(arr.join(","));

    let minIndex = i;

    for (let j = i + 1; j < n; j++) {
      if (arr[j] < arr[minIndex]) {
        minIndex = j;
      }
    }

    const minValue = arr[minIndex];

    // Shift all elements between minIndex and i forward by one place
    for (let k = minIndex; k > i; k--) {
      arr[k] = arr[k - 1];
    }

    // Place the minimum value at the correct position
    arr[i] = minValue;
  }

  console.log(arr.join(","));

}


module.exports = [selectionSort, selectionSortInPlace];
