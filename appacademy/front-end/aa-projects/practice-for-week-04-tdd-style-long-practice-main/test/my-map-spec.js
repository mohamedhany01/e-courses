const chai = require("chai");
const spies = require("chai-spies");
chai.use(spies);
const { expect } = chai;

const myMap = require("../problems/my-map");

describe("myMap (Valid arguments)", () => {
    let arr;

    beforeEach(() => {
        arr = [1, 2, 3, 4, 5];
        cb = chai.spy((el) => el * 2);
    });

    it("shouldn't mutate the passed array", () => {
        const result = myMap(arr, cb);

        expect(result).to.deep.equal([2, 4, 6, 8, 10]);
        expect(arr).to.deep.equal([1, 2, 3, 4, 5]);
    });


    it("should not call the built-in Array.map", () => {
        const mapSpy = chai.spy.on(Array.prototype, "map");

        myMap(arr, cb);

        expect(mapSpy).to.not.have.been.called();
    });

    it("should call the callback once for each element in the array", function () {
        myMap(arr, cb);

        expect(cb).to.have.been.called.exactly(5).called();
    });

    it("should return empty array", function () {
        const result = myMap([], cb);

        expect(result).to.be.empty;
        expect(result.length).to.be.equal(0);
    });

});

describe("myMap (Invalid arguments)", () => {

    it("should throw error when passing invalid array", () => {
        const result = () => myMap("arr", cb);

        expect(result).to.throw(TypeError);
    });

});