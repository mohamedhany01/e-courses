const assert = require("assert");
const chai = require("chai");
const { divideByThree, averageOfTwo, averageOfFour, doubler, combineArrays, wordWithinArray, echo, fizzBuzz, hello, goodbye, isFive, isOdd, isSubString, aCounter } = require("../problems");
const expect = chai.expect;

describe("Problems", function () {
    describe("divideByThree", function () {
        it('should divide input by 3', function () {
            expect(divideByThree(3)).to.equal(1);
            expect(divideByThree(1)).to.equal(1 / 3);
            expect(divideByThree(0)).to.equal(0);
        })
    })

    describe("averageOfTwo", function () {
        it('should take average of two inputs', function () {
            expect(averageOfTwo(1, 2)).to.equal(1.5);
            expect(averageOfTwo(2, 4)).to.equal(3);
        })
    })

    describe("averageOfFour", function () {
        it('should take average of four inputs', function () {
            expect(averageOfFour(1, 2, 3, 4)).to.equal(2.5);
        })
    })

    describe("doubler", function () {
        it('should double every element in an array', function () {
            expect(doubler([1, 2, 3])).to.deep.equal([2, 4, 6]);
        })
    })

    describe("combineArrays", function () {
        it('should concatenate two input arrays', function () {
            expect(combineArrays([1, 2], [3, 4])).to.deep.equal([1, 2, 3, 4]);
        })
    })

    describe("wordWithinArray", function () {
        it('should return true if word is within array', function () {
            expect(wordWithinArray("hello", ["yes", "no", "hello", "goodbye"])).to.equal(true);
        })
    })

    describe("echo", function () {
        it('should return string in format [UPPERCASE] ... [Original] ... [LOWERCASE] ', function () {
            expect(echo('Hello')).to.equal("HELLO ... Hello ... hello");
        })
    })

    describe("fizzBuzz", function () {
        it('should return array of numbers from 0 to max (not inclusive) that are divisible by 3 or 5 but not both', function () {
            expect(fizzBuzz(20)).to.deep.equal([3, 5, 6, 9, 10, 12, 18]);
        })
    })

    describe("hello", function() {
        it('should return the string as "Hello, {input}"', function() {
            expect(hello('John')).to.equal("Hello, John");
        })
    })

    describe("goodbye", function() {
        it('should return the string as "Bye, {input}"', function() {
            expect(goodbye('Lee')).to.equal("Bye, Lee");
        })
    })

    describe("isFive", function() {
        it('should return true if input is 5, false otherwise', function() {
            expect(isFive(5)).to.equal(true);
            expect(isFive(1)).to.equal(false);
        })
    })

    describe("isOdd", function() {
        it('should return true if input is odd, false otherwise', function() {
            expect(isOdd(21)).to.equal(true);
            expect(isOdd(0)).to.equal(false);
        })
    })

    describe("isSubstring", function() {
        it('should return true is searchString contains subString', function() {
            expect(isSubString("I love App Academy", "app")).to.equal(true);
            expect(isSubString("I love App Academy", "hello")).to.equal(false);
        })
    })

    describe("aCounter", function() {
        it('should count the number of instances of the letter "a"', function() {
            expect(aCounter("There are a few cats in attic today")).to.equal(5);
            expect(aCounter("A day without sun is sad")).to.equal(3);
        })
    })

});
