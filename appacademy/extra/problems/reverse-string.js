// your code here
const reverseString = function(word) {
    word = word.split("");
    word = word.reverse();
    word = word.join("");

    return word;
}



console.log(reverseString('fish')); // 'hsif'
console.log(reverseString('marathon')); // 'nohtaram'
