# Array practice

Identify the time complexity of each of these functions with a 1 sentence
justification for your answer. Assume `arr` is an array of length _n_.

## `arr.push()`

Time complexity: O(1)
Space complexity: O(1)
Justification: Does one operation on the array

[push on MDN][push]


## `arr.pop()`

Time complexity: O(1)
Space complexity: O(1)
Justification: Does one operation on the array

[pop on MDN][pop]

## `arr.shift()`

Time complexity: O(N)
Space complexity: O(1)
Justification: After shifting the array needs to be rearranged

[shift on MDN][shift]

## `arr.unshift()`

Time complexity: O(N)
Space complexity: O(N)
Justification: After unshifting the array needs to be rearranged and create a new array

[unshift on MDN][unshift]

## `arr.splice()`

Time complexity: O(N)
Space complexity: O(N)
Justification: After splice the array needs to be rearranged and create a new array

[splice on MDN][splice]

## `arr.slice()`

Time complexity: O(N)
Space complexity: O(N)
Justification: After slice the array needs to be rearranged and create a new array

[slice on MDN][slice]

## `arr.indexOf()`

Time complexity: O(N)
Space complexity: O(1)
Justification: Need to loop on the whole array 

[indexOf on MDN][indexOf]

## `arr.map()`

Time complexity: O(N)
Space complexity: O(N)
Justification: Need to loop on the whole array and create a new array

[map on MDN][map]

## `arr.filter()`

Time complexity: O(N)
Space complexity: O(N)
Justification: Need to loop on the whole array and create a new array

[filter on MDN][filter]

## `arr.reduce()`

Time complexity: O(N)
Space complexity: O(1)
Justification: Need to loop on the whole array

[reduce on MDN][reduce]

## `arr.reverse()`

Time complexity: O(N)
Space complexity: O(1)
Justification: Need to loop on the whole array

[reverse on MDN][reverse]

## `[...arr]`

Time complexity: O(N)
Space complexity: O(N)
Justification: Need to loop on the whole array and create a new array

[spread on MDN][spread]

[push]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/push
[pop]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/pop
[shift]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/shift
[unshift]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/unshift
[splice]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/splice
[slice]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/slice
[indexOf]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/indexOf
[map]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/map
[filter]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/filter
[reduce]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/reduce
[reverse]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/reverse
[spread]:https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Spread_syntax