module.exports = Array.prototype.isEqual = function (other) {

    for (let i = 0; i < arguments.length; i++) {
        if (this[i] !== other[i]) {
            return false;
        }
    }

    return true;
}