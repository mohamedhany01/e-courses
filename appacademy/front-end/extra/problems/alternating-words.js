// Your code here
const alternatingWords = function(array) {
    for (let i = 0; i < array.length - 1; i+=2) {
        array[i] = array[i].toUpperCase();
        array[i + 1] = array[i + 1].toLowerCase();
    }

    // off-by-one
    if(array.length % 2 === 0) {
        array[array.length - 1] = array[array.length - 1].toLowerCase();
    } else {
        array[array.length - 1] = array[array.length - 1].toUpperCase();
    }

    return array;
}


let words1 = ['Belka', 'STRELKA', 'laika', 'DEZIK', 'Tsygan'];
alternatingWords(words1);
console.log(words1); // [ 'BELKA', 'strelka', 'LAIKA', 'dezik', 'TSYGAN' ]

let words2 = ['Yellowstone', 'Yosemite', 'Zion', 'Acadia'];
alternatingWords(words2);
console.log(words2); // [ 'YELLOWSTONE', 'yosemite', 'ZION', 'acadia' ]
