// your code here
const strangeSums = function (array) {
    let counter = 0;

    for (let i = 0; i < array.length; i++) {
        const current = Math.abs(array[i]);

        for (let j = i + 1; j < array.length; j++) {
            const next = Math.abs(array[j]);

            if (current === next) {
                counter++;
            }
        }

    }
    return counter;
}


console.log(strangeSums([2, -3, 3, 4, -2]));     // 2
console.log(strangeSums([42, 3, -1, -42]));      // 1
console.log(strangeSums([-5, 5]));               // 1
console.log(strangeSums([19, 6, -3, -20]));      // 0
console.log(strangeSums([9]));                   // 0
