class DynamicArray {

  constructor(defaultSize = 4) {
    this.capacity = defaultSize;
    this.data = new Array(defaultSize);
    this.length = 0;
  }

  read(index) {
    return this.data[index];
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

  _needResize(dArray) {
    return dArray.length === dArray.capacity;
  }

  _resize(dArray) {
    dArray.capacity = dArray.length * 2;

    let bigArray = new Array(dArray.capacity);

    for (let i = 0; i < dArray.length; i++) {
      bigArray[i] = dArray.data[i];
    }

    dArray.data = bigArray;
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

  unshift(val) {
    
    if (this.length == 0) {

      this.data[this.length] = val;

      return ++this.length;
    }

    if (this._needResize(this)) {
      this._resize(this);
    }

    this._shiftArrayItems(this.length, this.data);

    this.data[0] = val;

    return ++this.length;
  }
}


module.exports = DynamicArray;