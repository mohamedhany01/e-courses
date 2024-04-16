const { containsOnlyASCIICharacters, removeAllSpaces, reverseString } = require("../problems/reverse-string");

const { expect } = require("chai");

describe("reverse string", () => {
    it("should return a reversed string (idea case)", () => {
        const expected = "cba";
        const actual = reverseString("abc");

        expect(actual).to.equal(expected);
    });

    it("should return a reversed string no spaces", () => {
        const expected = "cba";
        const actual = reverseString(" a b c ");

        expect(actual).to.equal(expected);
    });

    it("should return a reversed string with special characters", () => {
        const expected = ")(*&^%$#@!cba";
        const actual = reverseString("abc!@#$%^&*()");
        expect(actual).to.equal(expected);
    });

    it("should return null in case the string is empty", () => {
        const expected = null;
        const actual = reverseString("");

        expect(actual).to.equal(expected);
    });

    it("should return null in case the string is only spaces", () => {
        const expected = null;
        const actual = reverseString("      ");

        expect(actual).to.equal(expected);
    });

    it("should return the first character in case the string is one char", () => {
        const expected = "a";
        const actual = reverseString("a");

        expect(actual).to.equal(expected);
    });

    it("should throw error with lengthy string", () => {
        const longWord = "pneumonoultramicroscopicsilicovolcanoconiosis" + "x";

        const result = () => reverseString(longWord);

        expect(result).to.throw(Error);

    });

    it("should throw error with not type of string", () => {

        const result = () => reverseString(1);

        expect(result).to.throw(TypeError);

    });

    it("should throw error with not type of non ASCII string", () => {

        const result = () => reverseString("👌");

        expect(result).to.throw(Error);

    });
});


describe("no spaces", () => {
    it("should return a string without edge spaces", () => {
        const expected = "abc";
        const actual = removeAllSpaces(" abc ");

        expect(actual).to.equal(expected);
    });

    it("should return a string without middle spaces", () => {
        const expected = "abc";
        const actual = removeAllSpaces("a b c");

        expect(actual).to.equal(expected);
    });
});

describe("are all characters ASCII", () => {
    it("should return true to ASCII string", () => {
        const result = containsOnlyASCIICharacters("abc!@#$%^&*()");

        expect(result).to.be.true;
    });

    it("should return false to non ASCII string", () => {
        const result = containsOnlyASCIICharacters("👌");

        expect(result).to.be.false;
    });
});

