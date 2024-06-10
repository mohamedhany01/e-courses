const chia = require("chai");
const spies = require("chai-spies");
chia.use(spies);

const { expect } = chia;

const { returnsThree, reciprocal } = require("../problems/number-fun");

describe("returnsThree() function", () => {

    it("should return three", () => {
        const expected = 3;
        const actual = returnsThree();

        expect(actual).to.equal(expected);
    });
});

describe("reciprocal() function", () => {

    context("reciprocal() (valid arguments)", () => {

        it("should return 1", () => {
            const expected = 1;
            const actual = reciprocal(1);

            expect(actual).to.equal(expected);
        });

        it("should return 1/3", () => {
            const expected = 1 / 3;
            const actual = reciprocal(3);

            expect(actual).to.equal(expected);
        });

        it("should return 1/1000000", () => {
            const expected = 1 / 1000000;
            const actual = reciprocal(1000000);

            expect(actual).to.equal(expected);
        });

        it("should return 0.5 for 2", () => {
            const expected = 0.5;
            const actual = reciprocal(2);

            expect(actual).to.equal(expected);
        });

    });

    context("reciprocal() (invalid arguments)", () => {

        it("should throw error in case the argument is not a number", () => {
            const result = () => reciprocal("3");

            expect(result).to.throw(TypeError);
        });

        it("should throw error in case the argument is less than 1", () => {
            const result = () => reciprocal(0);

            expect(result).to.throw(RangeError);
        });

        it("should throw error in case the argument is greater than 1000000", () => {
            const result = () => reciprocal(1000001);

            expect(result).to.throw(RangeError);
        });
    });
});