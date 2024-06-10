const assert = require("assert");
const chai = require("chai");
const { plusTen, returnSevens, bothStringsIncluded, productArray, fiveAndEleven, countConsonants, alternatingLetters } = require("../problems");
const expect = chai.expect;

describe("Problems", function () {
    describe("plusTen", function () {
        it('should add 10 to input', function () {
            expect(plusTen(0)).to.equal(10);
        });
    })

    describe("returnSevens", function () {
        it('should return array of all multiples of 7 less than input', function () {
            expect(returnSevens(50)).to.deep.equal([0, 7, 14, 21, 28, 35, 42, 49])
        });
        it('should not check if `max` is a multiple of 7', function () {
            expect(returnSevens(49)).to.deep.equal([0, 7, 14, 21, 28, 35, 42]);
        })
    })

    describe("bothStringsIncluded", function () {
        it('should return true if both strings are in the sentence', function () {
            expect(bothStringsIncluded("I'm having a great time", "great", "nope")).to.equal(false);
            expect(bothStringsIncluded("I'm having a great time", "great", "time")).to.equal(true);
            expect(bothStringsIncluded("I'm having a great time", "wrong", "words")).to.equal(false);
        });
    })

    describe("productArray", function () {
        it('should return the product of all the elemnts in the array', function () {
            expect(productArray([1, 2, 3, 4, 5])).to.equal(120);
            expect(productArray([0, 1, 2, 3, 4, 5])).to.equal(0);
            expect(productArray([1, -2, 3, 4, 5])).to.equal(-120);
        });
    })

    describe("fiveAndEleven", function () {
        it('should return true if the input is divisible by both 5 and 11', function () {
            expect(fiveAndEleven(55)).to.equal(true);
            expect(fiveAndEleven(110)).to.equal(true);
            expect(fiveAndEleven(50)).to.equal(false);
            expect(fiveAndEleven(44)).to.equal(false);
        });
    })

    describe("countConsonants", function () {
        it('should return the number of consonants in the input', function () {
            expect(countConsonants("nospaces")).to.equal(5);
            expect(countConsonants("yes spaces")).to.equal(6);
        });
    })

    describe("alternatingLetters", function () {
        it('should return the string with alternating cases starting with lowercase', function () {
            expect(alternatingLetters("this is a test")).to.equal("tHiS Is a tEsT");
            expect(alternatingLetters("test")).to.equal("tEsT");
        });
    })
});
