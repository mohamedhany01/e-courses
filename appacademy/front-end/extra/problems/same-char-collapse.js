const sameCharCollapse = function (str) {
    let flag = true;


    while (flag) {
        let result = str.split("");
        flag = false;

        for (let i = 0; i < result.length - 1; i++) {
            const currentChar = result[i];
            const nextChar = result[i + 1];

            if (currentChar === nextChar) {
                result[i] = "";
                result[i + 1] = "";
                flag = true;
            }
            str = result.join("");
        }
    }

    return str;
}

console.log(sameCharCollapse("zzzxaaxy"));  // "zy"
// because zzzxaaxy -> zxaaxy -> zxxy -> zy
console.log(sameCharCollapse("uqrssrqvtt")); // "uv"
// because uqrssrqvtt -> uqrrqvtt -> uqqvtt -> uvtt -> uv
