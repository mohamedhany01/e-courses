// Your code here
const unique = function(array) {
    const result = [];
    result.push(array[0]);

    for (let i = 1; i < array.length; i++) {

        if(result.includes(array[i])) continue;

        result.push(array[i]);
    }

    return result;
}



console.log(unique([1, 1, 2, 3, 3])); // [1, 2, 3]
console.log(unique([11, 7, 8, 10, 8, 7, 7])); // [11, 7, 8, 10]
console.log(unique(['a', 'b', 'c', 'b'])); // ['a', 'b', 'c']
