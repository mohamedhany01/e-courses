function myMap(inputArray, callback) {

  if (!Array.isArray(inputArray)) {
    throw new TypeError("Input is not an array");
  }

  const result = [];

  for (const value of inputArray) {
    result.push(callback(value));
  }

  return result;
}

module.exports = myMap;