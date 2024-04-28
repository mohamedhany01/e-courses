class DynamicArray {

  constructor(defaultSize = 4) {
    this.capacity = defaultSize;
    this.data = new Array(defaultSize);
    this.length = 0;
  }

  _needResize(dArray) {
    return dArray.length === dArray.capacity;
  }

  _shiftArrayItems(length, array) {
    let lastElementIndex = length - 1;
    let lastElement = array[lastElementIndex];

    for (let i = 0; i < length; i++) {
      let newIndex = lastElementIndex + 1;
      array[newIndex] = lastElement;

      lastElementIndex -= 1;
      lastElement = array[lastElementIndex];
    }
  }

  read(index) {

    return this.data[index];
  }

  push(val) {

    if (this._needResize(this)) {
      this.resize();
    }
    this.data[this.length++] = val;
  }


  pop() {
    if (this.length === 0) {
      return this.data[-1];
    }

    if (this.length === 1) {
      const data = this.data[--this.length];
      this.length = 0;
      return data;
    }

    return this.data[--this.length];
  }

  shift() {
    if (this.length === 0) {
      return this.data[-1];
    }

    let currentIndex = 0;

    const firstItem = this.data[currentIndex];

    let nextIndex = currentIndex + 1;

    while (currentIndex < this.length - 1) {
      let temp = this.data[nextIndex];
      this.data[currentIndex] = temp;
      currentIndex++;
      nextIndex = currentIndex + 1;
    }

    this.length--;

    return firstItem;
  }

  unshift(val) {

    if (this.length == 0) {

      this.data[this.length] = val;

      return ++this.length;
    }

    if (this._needResize(this)) {
      this.resize();
    }

    this._shiftArrayItems(this.length, this.data);

    this.data[0] = val;

    return ++this.length;
  }

  indexOf(val) {

    for (const index in this.data) {
      const numIndex = parseInt(index);

      if (this.read(numIndex) == val) {
        return numIndex;
      }
    }

    return -1;
  }

  resize() {

    this.capacity = this.length * 2;

    let bigArray = new Array(this.capacity);

    for (let i = 0; i < this.length; i++) {
      bigArray[i] = this.data[i];
    }

    this.data = bigArray;
  }

}


module.exports = DynamicArray;