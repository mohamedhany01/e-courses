const sha256 = require('js-sha256');

class KeyValuePair {
  constructor(key, value) {
    this.key = key;
    this.value = value;
    this.next = null;
  }
}

class HashTable {

  constructor(numBuckets = 4) {
    this.capacity = numBuckets;
    this.count = 0;
    this.data = new Array(this.capacity).fill(null);
  }

  hash(key) {
    const hash = sha256(key).slice(0, 8);
    const hashDecimal = parseInt(hash, 16);
    return hashDecimal;
  }

  hashMod(key) {
    return this.hash(key) % this.capacity;
  }

  insertNoCollisions(key, value) {
    const keyIndex = this.hashMod(key);

    const isExist = this.data[keyIndex];

    if (isExist) {
      throw new Error("hash collision or same key/value pair already exists!");
    }

    this.data[keyIndex] = { key: key, value: value };

    this.count++;
  }

  insertWithHashCollisions(key, value) {
    try {
      this.insertNoCollisions(key, value);
    } catch (error) {
      const newPairs = new KeyValuePair(key, value);
      const currentHead = this.data[0];

      this.data[0] = newPairs;
      newPairs.next = currentHead;

      this.count++;
    }
  }

  _getKey(key) {
    // const isExist = this.data[keyIndex];

    for (let i = 0; i < this.data.length; i++) {
      const bucket = this.data[i];

      if (bucket?.next) {
        let head = bucket;

        while (head) {
          if (head.key === key) {
            return head;
          }

          head = head.next;
        }

        continue;
      }

      if (bucket?.key === key) {
        return bucket;
      }

      return false;
    }

  }

  insert(key, value) {

    const bucket = this._getKey(key);
    const isSameKey = bucket?.key === key;

    if (bucket && isSameKey) {
      bucket.value = value;
    } else {
      this.insertWithHashCollisions(key, value);
    }
  }

}


module.exports = HashTable;