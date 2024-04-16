function reverseString(str) {

  if (!isString(str)) {
    throw new TypeError("Input is not a string");
  }

  const stringWithoutSpaces = removeAllSpaces(str);

  if (stringWithoutSpaces.length === 0) {
    return null;
  }

  if (!containsOnlyASCIICharacters(str)) {
    throw new Error("Contains non-ASCII characters");
  }

  if (isStringTooLong(stringWithoutSpaces)) {
    throw new Error("Input is too lengthy");
  }

  if (stringWithoutSpaces.length === 1) {
    return str;
  }

  return stringWithoutSpaces.split("").reverse().join("");
}

function isString(str) {
  return typeof str === 'string';
}

function isStringTooLong(str) {
  // pneumonoultramicroscopicsilicovolcanoconiosis
  const LONGEST_WORD_LENGTH = 45;

  return str.length > LONGEST_WORD_LENGTH;
}

function removeAllSpaces(str) {
  return str.split(" ").join("");
}

function containsOnlyASCIICharacters(str) {
  return [...str].some(char => char.charCodeAt(0) <= 255);
}

module.exports = { containsOnlyASCIICharacters, removeAllSpaces, reverseString }