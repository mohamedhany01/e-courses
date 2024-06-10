function anagrams(str1, str2) {
  if (str1.length !== str2.length) return false;

  const map = new Map();
  const len = str1.length;

  for (let i = 0; i < len; i++) {
    const charKey = str1[i];
    map.set(charKey, charKey);
  }

  for (let i = 0; i < len; i++) {
    if (!map.get(str2[i])) return false;
  }

  return true;
}


function commonElements(arr1, arr2) {
  let max = arr1;
  let min = arr2;

  if (arr2.length > max.length) {
    max = arr2;
    min = arr1;
  }

  const s1 = new Set(max);
  const s2 = new Set(min);

  const common = [...s1].filter(e => s2.has(e));

  return common;
}


function duplicate(arr) {
  const map = new Map();

  for (let i = 0; i < arr.length; i++) {
    if (map.get(arr[i])) {
      return arr[i];
    }
    map.set(arr[i], arr[i]);
  }

  return null;
}


function twoSum(nums, target) {
  const map = new Map();

  for (let i = 0; i < nums.length; i++) {
    const complement = target - nums[i];
    if (map.get(complement)) {
      return true;
    }
    map.set(nums[i], i);
  }

  return false;
}


function wordPattern(pattern, strings) {
  if (pattern.length !== strings.length) {
    return false;
  }

  const patternMap = new Map();
  const stringMap = new Map();

  for (let i = 0; i < pattern.length; i++) {
    const char = pattern[i];
    const word = strings[i];

    if (!patternMap.get(char) && !stringMap.get(word)) {
      patternMap.set(char, word);
      stringMap.set(word, char);
    } else {
      if (patternMap.get(char) !== word || stringMap.get(word) !== char) {
        return false;
      }
    }
  }

  return true;
}



module.exports = [anagrams, commonElements, duplicate, twoSum, wordPattern];