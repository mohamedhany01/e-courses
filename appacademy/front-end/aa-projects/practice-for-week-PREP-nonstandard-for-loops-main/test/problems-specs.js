const assert = require("assert");
const chai = require("chai");
const { oddIndices, oddReverse, secondPower, nthPower, firstHalf, secondHalf } = require("../problems");
const expect = chai.expect;

describe("Problems", function () {
    describe("oddIndices", function () {
        it('should return nothing on empty', function() {
            expect(oddIndices([])).to.deep.equal([]);
        });
        it('should return only odd indices', function () {
            expect(oddIndices([1, 2, 3])).to.deep.equal([2]);
            expect(oddIndices([1, 2, 3, 4])).to.deep.equal([2, 4]);
        });
        

    })

    describe("oddReverse", function () {
        it('should return nothing on empty', function() {
            expect(oddReverse([])).to.deep.equal([]);
        });
        it('should return odd indices in reverse', function () {
            expect(oddReverse([1, 2, 3])).to.deep.equal([2]);
            expect(oddReverse([1, 2, 3, 4])).to.deep.equal([4, 2]);
        });
    })

    describe("secondPower", function () {
        it('should return nothing on empty', function() {
            expect(secondPower([])).to.deep.equal([]);
        });
        it('should return only indices of powers of 2', function () {
            expect(secondPower([1, 2, 3, 4, 5, 6, 7, 8])).to.deep.equal([2, 3, 5]);
        });
    })

    describe("nthPower", function () {
        it('should return nothing on empty', function() {
            expect(nthPower([])).to.deep.equal([]);
        });
        describe('nth power', function () {
            it('should return 2nd power indices', function() {
                expect(nthPower([1, 2, 3, 4, 5, 6, 7, 8], 2)).to.deep.equal([2, 3, 5]);
            });
            it('should return 3rd power indices', function() {
                expect(nthPower([1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 3)).to.deep.equal([2, 4, 10]);
            });
            it('should return 4th power indices', function() {
                expect(nthPower([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17], 4)).to.deep.equal([2, 5, 17]);    
            });
        });
    })

    describe("firstHalf", function () {
        it('should return nothing on empty', function() {
            expect(firstHalf([])).to.deep.equal([]);
        });
        it('should return only the first half of the array', function () {
            expect(firstHalf([1, 2])).to.deep.equal([1]);
        });
        it('should be the inclusive first half', function() {
            expect(firstHalf([1])).to.deep.equal([1]);
        });
    })

    describe("secondHalf", function () {
        it('should return nothing on empty', function() {
            expect(oddIndices([])).to.deep.equal([]);
        });
        it('should return only the second half the array', function () {
            expect(secondHalf([1, 2])).to.deep.equal([2]);
        });
        it('should be the exclusive first half', function() {
            expect(secondHalf([1])).to.deep.equal([]);
        });
    })
});
