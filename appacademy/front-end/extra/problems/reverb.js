const isVowel = function(char) {
    const vowels = "aeiou";
    return vowels.includes(char);
}

const isStr = function(str) {
    return typeof str === "string";
}

const reverb = function(word) {
    if(!isStr(word)) {
        return null;
    }

    const clean = word.toLowerCase();
    const len = word.length;

    for (let i = clean.length - 1; i > 0; i--) {
        const char = clean[i];

        if(isVowel(char)) {
            const lastPart = word.slice(i, len);

            return word + lastPart;
        }
    }
}



console.log(reverb('running')); // runninging
console.log(reverb('FAMILY'));  // FAMILYILY
console.log(reverb('trash'));   // trashash
console.log(reverb('DISH'));    // DISHISH
console.log(reverb(197393));    // null
