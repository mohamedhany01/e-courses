const chai = require("chai");
const spies = require('chai-spies');
chai.use(spies);
const { expect } = chai;

const { Triangle, Scalene, Isosceles, Right, Equilateral } = require("../problems/triangle");

describe("Triangle class", () => {

    describe("Triangle class (Valid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Triangle(10, 10, 4);
        });

        context("constructor() method", () => {

            it("should create a triangle object successfully with three sides", () => {
                expect(triangle).property('side1').to.be.exist;
                expect(triangle).property('side2').to.be.exist;
                expect(triangle).property('side3').to.be.exist;
            });
        });

        context("getPerimeter() method", () => {

            it("should return the triangle perimeter (24)", () => {
                const expected = 24;

                const actual = triangle.getPerimeter();

                expect(actual).to.equal(expected);
            });
        });

        context("hasValidSideLengths() method", () => {

            it("should return true on valid lengths", () => {
                const result = triangle.hasValidSideLengths();

                expect(result).to.be.true;
            });
        });

        context("validate() method", () => {

            it("should return true on valid lengths", () => {
                const result = triangle.validate();

                expect(result).to.be.true;
            });
        });

        context("getValidTriangles() static method", () => {

            it("should return only three valid triangles", () => {
                const triangles = [
                    new Triangle(10, 10, 4),
                    new Triangle(10, 10, 10),
                    new Triangle(10, 5, 7),
                    new Triangle(10, 1, 7),
                ];

                const result = Triangle.getValidTriangles(triangles);

                expect(result.length).to.be.equal(3);
            });

            it("shouldn't return no triangle", () => {
                const triangles = [
                    new Triangle(1, 1, 2),
                    new Triangle(1, 5, 2),
                    new Triangle(10, 1, 7),
                ];

                const result = Triangle.getValidTriangles(triangles);

                expect(result.length).to.be.equal(0);
            });

            it("should return null if an empty array passed", () => {
                const result = Triangle.getValidTriangles([]);

                expect(result).to.be.null;
            });
        });

    });

    describe("Triangle class (Invalid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Triangle(10, 6, 4);
        });

        context("constructor() method", () => {

            it("should throw error when not passing number", () => {
                const result = () => new Triangle("1", "2", "3");

                expect(result).to.throw(TypeError);
            });
        });

        context("hasValidSideLengths() method", () => {

            it("should return false when passing invalid lengths", () => {
                const result = triangle.hasValidSideLengths();

                expect(result).to.be.false;
            });
        });

        context("validate() method", () => {

            it("should return false on invalid lengths", () => {
                const result = triangle.validate();

                expect(result).to.be.false;
            });
        });

        context("getValidTriangles() static method", () => {

            it("should throw error when not passing an array", () => {
                const result = () => Triangle.getValidTriangles("[]");

                expect(result).to.throw(TypeError);
            });
        });
    });
});

describe("Scalene class", () => {

    describe("Scalene class (Valid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Scalene(4, 2, 3);
        });

        context("constructor() method", () => {

            it("should create a triangle object successfully with three sides", () => {
                expect(triangle).property('side1').to.be.exist;
                expect(triangle).property('side2').to.be.exist;
                expect(triangle).property('side3').to.be.exist;
            });

            it("should be subclass of Triangle class", () => {
                expect(triangle instanceof Triangle).to.be.true;;
            });
        });

        context("getPerimeter() method", () => {

            it("should return the triangle perimeter (9)", () => {
                const expected = 9;

                const actual = triangle.getPerimeter();

                expect(actual).to.equal(expected);
            });
        });

        context("isScalene() method", () => {

            it("should be a scalene", () => {
                expect(triangle.isValidScalene).to.be.true;
            });
        });

        context("validate() method", () => {

            it("should overrides the validate() method in the Scalene", () => {
                const newTriangle = new Scalene(10, 10, 4);

                expect(newTriangle.isValidTriangle).to.be.true;

                expect(newTriangle.isValidScalene).to.be.false;
            });

            it("should be a valid triangle", () => {
                expect(triangle.isValidTriangle).to.be.true;
            });
        });

    });

    describe("Scalene class (Invalid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Scalene(1, 1, 10);
        });

        context("isScalene() method", () => {

            it("shouldn't be a valid triangle", () => {
                expect(triangle.isValidScalene).to.be.false;
            });
        });

        context("validate() method", () => {

            it("shouldn't be a valid scalene triangle", () => {
                expect(triangle.isValidScalene).to.be.false;
            });
        });
    });
});

describe("Isosceles class", () => {

    describe("Isosceles class (Valid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Isosceles(6, 6, 3);
        });

        context("constructor() method", () => {

            it("should create a triangle object successfully with three sides", () => {
                expect(triangle).property('side1').to.be.exist;
                expect(triangle).property('side2').to.be.exist;
                expect(triangle).property('side3').to.be.exist;
            });

            it("should be subclass of Triangle class", () => {
                expect(triangle instanceof Triangle).to.be.true;
            });
        });

        context("getPerimeter() method", () => {

            it("should return the triangle perimeter (15)", () => {
                const expected = 15;

                const actual = triangle.getPerimeter();

                expect(actual).to.equal(expected);
            });
        });

        context("isIsosceles() method", () => {

            it("should be a isosceles", () => {
                expect(triangle.isValidIsosceles).to.be.true;
            });
        });

        context("validate() method", () => {

            it("should overrides the validate() method in the Isosceles", () => {
                debugger
                const newTriangle = new Isosceles(10, 11, 4);

                expect(newTriangle.isValidTriangle).to.be.true;

                expect(newTriangle.isValidIsosceles).to.be.false;
            });

            it("should be a valid triangle", () => {
                expect(triangle.isValidTriangle).to.be.true;
            });
        });
    });

    describe("Isosceles class (Invalid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Isosceles(1, 2, 10);
        });

        context("isIsosceles() method", () => {

            it("shouldn't be a valid triangle", () => {
                expect(triangle.isValidIsosceles).to.be.false;
            });
        });

        context("validate() method", () => {

            it("shouldn't be a valid isosceles triangle", () => {
                expect(triangle.isValidIsosceles).to.be.false;
            });
        });
    });
});

describe("Right class", () => {

    describe("Right class (Valid arguments)", () => {

        let triangle;

        beforeEach(() => {

            triangle = new Right(24, 10, 26);
        });

        context("constructor() method", () => {

            it("should create a triangle object successfully with three sides", () => {
                expect(triangle).property('side1').to.be.exist;
                expect(triangle).property('side2').to.be.exist;
                expect(triangle).property('side3').to.be.exist;
            });

            it("should be subclass of Triangle class", () => {
                expect(triangle instanceof Triangle).to.be.true;
            });
        });

        context("getPerimeter() method", () => {

            it("should return the triangle perimeter (60)", () => {
                const expected = 60;

                const actual = triangle.getPerimeter();

                expect(actual).to.equal(expected);
            });
        });

        context("isRight() method", () => {

            it("should be a Right", () => {
                expect(triangle.isValidRight).to.be.true;
            });
        });

        context("validate() method", () => {

            it("should overrides the validate() method in the right", () => {
                const newTriangle = new Right(10, 11, 4);

                expect(newTriangle.isValidTriangle).to.be.true;

                expect(newTriangle.isValidRight).to.be.false;
            });

            it("should be a valid triangle", () => {
                expect(triangle.isValidTriangle).to.be.true;
            });
        });
    });

    describe("Right class (Invalid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Right(1, 2, 10);
        });

        context("isRight() method", () => {

            it("shouldn't be a valid triangle", () => {
                expect(triangle.isValidRight).to.be.false;
            });
        });

        context("validate() method", () => {

            it("shouldn't be a valid right triangle", () => {
                expect(triangle.isValidRight).to.be.false;
            });
        });
    });
});

describe("Equilateral class", () => {

    describe("Equilateral class (Valid arguments)", () => {

        let triangle;

        beforeEach(() => {

            triangle = new Equilateral(10, 10, 10);
        });

        context("constructor() method", () => {

            it("should create a triangle object successfully with three sides", () => {
                expect(triangle).property('side1').to.be.exist;
                expect(triangle).property('side2').to.be.exist;
                expect(triangle).property('side3').to.be.exist;
            });

            it("should be subclass of Triangle class", () => {
                expect(triangle instanceof Triangle).to.be.true;
            });
        });

        context("getPerimeter() method", () => {

            it("should return the triangle perimeter (30)", () => {
                const expected = 30;

                const actual = triangle.getPerimeter();

                expect(actual).to.equal(expected);
            });
        });

        context("isEquilateral() method", () => {

            it("should be a equilateral", () => {
                expect(triangle.isValidEquilateral).to.be.true;
            });
        });

        context("validate() method", () => {

            it("should overrides the validate() method in the equilateral", () => {
                const newTriangle = new Equilateral(10, 11, 4);

                expect(newTriangle.isValidTriangle).to.be.true;

                expect(newTriangle.isValidEquilateral).to.be.false;
            });

            it("should be a valid triangle", () => {
                expect(triangle.isValidTriangle).to.be.true;
            });
        });
    });

    describe("Equilateral class (Invalid arguments)", () => {

        let triangle;

        beforeEach(() => {
            triangle = new Equilateral(1, 2, 10);
        });

        context("isEquilateral() method", () => {

            it("shouldn't be a valid triangle", () => {
                expect(triangle.isValidEquilateral).to.be.false;
            });
        });

        context("validate() method", () => {

            it("shouldn't be a valid equilateral triangle", () => {
                expect(triangle.isValidEquilateral).to.be.false;
            });
        });
    });
});
