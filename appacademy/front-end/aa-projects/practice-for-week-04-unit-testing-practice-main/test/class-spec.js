const chai = require("chai");
const expect = chai.expect;

const { Word } = require("../class");

describe("Word", function () {

  let word;

  beforeEach(function () {
    word = new Word("auoFooBuzAUO");
  });


  describe("Word constructor function", function () {
    it('should have a "word" property', function () {
      expect(word.word).to.equal("auoFooBuzAUO");
    });

    it('should set the "word" property when a new word is created', function () {
      word.word = "Buz";

      expect(word.word).to.equal("Buz");
    });
  });

  describe("removeVowels function", function () {
    it("should return a the word with all vowels removed", function () {
      const expected = "FBz";
      const actual = word.removeVowels();

      expect(expected).to.equal(actual);
    });
  });

  describe("removeConsonants function", function () {
    it("should return the word with the consonants removed", function () {
      const expected = "auooouAUO";
      const actual = word.removeConsonants();

      expect(expected).to.equal(actual);
    });
  });

  describe("pigLatin function", function () {
    it("should return the word converted to pig latin", function () {
      word.word = "Foo"

      const expected = "ooFay";
      const actual = word.pigLatin();

      expect(expected).to.equals(actual);
    });
  });
});