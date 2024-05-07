class KeyValuePair {
  constructor(key, value) {
    this.key = key;
    this.value = value;
    this.next = null;
  }
}

class HashTable { // get O(1), set O(1), deleteKey O(1)

  constructor(numBuckets = 8) {
    this.capacity = numBuckets;
    this.count = 0;
    this.data = new Array(this.capacity).fill(null);
  }

  hash(key) {
    let hashValue = 0;

    for (let i = 0; i < key.length; i++) {
      hashValue += key.charCodeAt(i);
    }

    return hashValue;
  }

  hashMod(key) {
    // Get index after hashing
    return this.hash(key) % this.capacity;
  }


  insert(key, value) {

    if (this.count / this.capacity >= 0.75) {
      // debugger;
      this.resize();
    }

    // Insert with collision
    const bucket = this.hasCollision(key);
    const keyIndex = this.hashMod(key);

    if (!bucket) {
      this.data[keyIndex] = { key: key, value: value };
      this.count++;
      return;
    }

    if (bucket) {
      if (bucket.next) {
        let currentBucket = bucket;

        while (currentBucket) {
          if (currentBucket.key === key) {
            currentBucket.value = value;

            return;
          }

          currentBucket = currentBucket.next;
        }
      }

      const newBucket = new KeyValuePair(key, value);
      newBucket.next = bucket;
      this.data[keyIndex] = newBucket;
      this.count++;
    }
  }

  hasCollision(key) {
    const keyIndex = this.hashMod(key);

    return this.data[keyIndex];
  }


  read(key) {
    const index = this.hashMod(key);
    const bucket = this.data[index];

    if (bucket?.next) {
      let next = bucket;

      while (next) {
        if (next && next.key === key) {
          return next.value;
        }
        next = next.next;
      }
    }

    if (bucket && bucket.key === key) {
      return bucket.value;
    }

    return undefined;
  }


  resize() {
    this.capacity = 2 * this.capacity;
    const oldList = this.data;

    this.data = new Array(this.capacity).fill(null);

    this.count = 0;
    for (let i = 0; i < oldList.length; i++) {
      if (oldList[i]?.next) {

        let next = oldList[i];

        while (next) {
          const { key, value } = next;
          this.insert(key, value);

          next = next.next;
        }

      } else {
        const bucket = oldList[i];

        if (!bucket) continue;

        const { key, value } = bucket;
        this.insert(key, value);
      }
    }
  }


  delete(key) {
    const index = this.hashMod(key);
    let bucket = this.data[index];

    if (bucket && !bucket.next && key === bucket.key) {
      this.data[index] = null;
      this.count--;
      return;
    }

    if (bucket?.next) {
      let next = bucket;
      let prev = bucket;

      if (next && next.key === key) {
        this.data[index] = next.next;
        this.count--;
        return;
      }

      while (next) {
        if (next && next.key === key) {
          prev.next = prev.next.next;
          this.count--;
          return;
        }
        prev = next;
        next = next.next;
      }
    }

    return "Key not found";
  }
}


module.exports = HashTable;