// your code here
const removeEWords = function(word) {

    word = word.split(" ");
    const result = [];

    for (let i = 0; i < word.length; i++) {

        if(word[i].includes("e")|| word[i].includes("E")) continue;

        result.push(word[i]);
    }

    word = result.join(" ");

    return word;
}


console.log(removeEWords('What time is it everyone?')); // 'What is it'
console.log(removeEWords('Enter the building')); // 'building'
