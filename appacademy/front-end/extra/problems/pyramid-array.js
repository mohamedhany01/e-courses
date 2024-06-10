// For example, given 2, 3, 7, 5, 9 as the base, we should construct this pyramid:
//
//           85
//         37  48
//       15  22  26
//    5   10   12   14
//  2   3    7    5    9
//
//
// Your code here

const buildMatrixPyramid = function (array) {
    const result = [];
    result.push(array);

    for (let i = 0; i < array.length - 1; i++) {
        const newArray = [];
        const topEntry = result[0];

        for (let j = 0; j < topEntry.length - 1; j++) {
            const n1 = topEntry[j];
            const n2 = topEntry[j + 1];
            const element = n1 + n2;

            newArray.push(element)
        }

        result.unshift(newArray);
    }

    return result;
}

const pyramidArray = function (array) {
    const matrix = buildMatrixPyramid(array);

    return matrix;
}



let p1 = pyramidArray([2, 3, 7, 5, 9]);
console.log(p1);
// [
//   [ 85 ],
//   [ 37, 48 ],
//   [ 15, 22, 26 ],
//   [ 5, 10, 12, 14 ],
//   [ 2, 3, 7, 5, 9 ]
// ]

// let p2 = pyramidArray([2, 2, 2, 2]);
// console.log(p2);
// [
//   [ 16 ],
//   [ 8, 8 ],
//   [ 4, 4, 4 ],
//   [ 2, 2, 2, 2 ]
// ]



const buildPyramid = function (array) {
    let spaces = array.length * 4;

    for (let i = 0; i < array.length; i++) {
        let line = "";

        for (let j = 0; j < spaces; j++) {
            line += " ";
        }

        spaces -= 3;

        const subArray = array[i];

        for (let j = 0; j < subArray.length; j++) {
            line += subArray[j] + "   ";
        }

        console.log(line);
    }
}
