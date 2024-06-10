// your code here
const pairsMaker = function(array) {
    const result = [];

    for (let i = 0; i < array.length; i++) {
        const currentChar = array[i];

        for (let j = i + 1; j < array.length; j++) {
            const nextChar = array[j];

            result.push([currentChar, nextChar]);
        }
    }

    return result;
}





console.log(pairsMaker(['a', 'b', 'c', 'd'])); // =>
// [ [ 'a', 'b' ],
//   [ 'a', 'c' ],
//   [ 'a', 'd' ],
//   [ 'b', 'c' ],
//   [ 'b', 'd' ],
//   [ 'c', 'd' ] ]

console.log(pairsMaker(['Rosemary', 'Alex', 'Connor'])); // =>
// [ [ 'Rosemary', 'Alex' ],
//   [ 'Rosemary', 'Connor' ],
//   [ 'Alex', 'Connor' ] ]
