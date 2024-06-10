// your code here

const isVowel = function(char) {
    const vowels = "aeiou";
    return vowels.includes(char);
}

const isStr = function(str) {
    return typeof str === "string";
}

const removeLastVowel = function(word) {
    for (let i = word.length - 1; i > 0; i--) {
        const char = word[i];

        if(isVowel(char)) {
            const len = word.length;

            const firstPart = word.slice(0, i)

            const lastPart = word.slice(i + 1, len);

            word = firstPart + lastPart;

            return word;
        }
    }

    return word;
}


console.log(removeLastVowel('bootcamp')); // 'bootcmp'
console.log(removeLastVowel('better')); // 'bettr'
console.log(removeLastVowel('graph')); // 'grph'
console.log(removeLastVowel('thy')); // 'thy'
