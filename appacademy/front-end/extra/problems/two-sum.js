// your code here

const twoSum = function(array, target) {
    for (let i = 0; i < array.length; i++) {
        const current = array[i];

        for (let j = i + 1; j < array.length; j++) {
            const next = array[j];

            if(current + next === target) {
                return true;
            }

        }
    }

    return false;
}






console.log(twoSum([1, 7, 3, 0, 2], 5)); // true
console.log(twoSum([1, 7, 3, 0, 2], 6)); // false
console.log(twoSum([4, 6, 2, 3], 8)); // true
console.log(twoSum([4, 6, 2, 3], 11)); // false
