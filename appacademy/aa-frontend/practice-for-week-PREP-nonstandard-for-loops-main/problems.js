function oddIndices(arr) {
    // Return an array containing all the odd indices in the array
    const result = [];

    if(arr.length === 0) return result;

    for (let i = arr[0]; i <= arr.length; i++) {

        if(i % 2 !== 0) continue;

        result.push(i);
    }

    return result;
}

function oddReverse(arr) {
    // Return an array containing all the odd indices starting from the end
    const result = [];

    if(arr.length === 0) return result;

    for (let i = arr.length; i > 0 ; i--) {

        if(i % 2 !== 0) continue;

        result.push(i);
    }

    return result;
}

function secondPower(arr) {
    // Return an array containing all indices that are powers of 2
    return arr.filter((_, index) => {
        let power = 1;
        while (power < index) {
            power *= 2;
        }
        return power === index;
    });
}

function nthPower(arr, n) {
    // Return an array containing all indices that are powers of n
    return arr.filter((_, index) => {
        let power = 1;
        while (power < index) {
            power *= n;
        }
        return power === index;
    });
}

function firstHalf(arr) {
    // Return an array containing the first half of an array
    // Include middle index on odd length arr
    const result = [];
    const half = arr.length / 2;


    if(arr.length === 0) return result;

    if(arr.length === 1 || half === 1) {
        result.push(arr[0]);

        return result;
    }

    for (let i = 0 ; i < half; i++) {
        result.push(arr[i]);
    }

    return result;
}

function secondHalf(arr) {
    // Return an array containing the second half of an array
    // Exclude middle index on odd length arr
    const result = [];
    let half = Math.round(arr.length / 2);


    if(arr.length === 0 || arr.length === 1) return result;

    for (let i = half ; i < arr.length; i++) {
        result.push(arr[i]);
    }

    return result;
}

module.exports = {
    oddIndices,
    oddReverse,
    secondPower,
    nthPower,
    firstHalf,
    secondHalf
}
