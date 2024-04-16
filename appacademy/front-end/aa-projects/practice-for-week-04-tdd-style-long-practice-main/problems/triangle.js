class Triangle {

    constructor(side1, side2, side3) {

        if (
            typeof side1 !== 'number' ||
            typeof side2 !== 'number' ||
            typeof side3 !== 'number'
        ) {
            throw new TypeError("The triangle sides must be a number");
        }

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;

        this.isValid = this.validate();
    }

    getPerimeter() {
        return this.side1 + this.side2 + this.side3;
    }

    hasValidSideLengths() {
        if (
            this.side2 + this.side3 > this.side1 &&
            this.side1 + this.side3 > this.side2 &&
            this.side1 + this.side2 > this.side3
        ) {
            return true;
        }

        return false;
    }

    validate() {
        return this.hasValidSideLengths();
    }

    static getValidTriangles(triangles) {

        if (!Array.isArray(triangles)) {

            throw new TypeError("Must provide a valid array of triangles");
        }

        if (triangles.length === 0) {
            return null;
        }

        return triangles.filter(triangle => triangle.isValid);
    }
}

class Scalene extends Triangle {
    constructor(side1, side2, side3) {
        super(side1, side2, side3);

        this.isValidTriangle = super.validate();
        this.isValidScalene = this.validate();
    }

    isScalene() {
        if (
            this.side1 !== this.side2 &&
            this.side2 !== this.side3 &&
            this.side1 !== this.side3
        ) {
            return true;
        }

        return false;
    }

    validate() {
        return this.isScalene();
    }
}

class Isosceles extends Triangle {
    constructor(side1, side2, side3) {
        super(side1, side2, side3);

        this.isValidTriangle = super.validate();
        this.isValidIsosceles = this.validate();
    }

    isIsosceles() {
        if (
            this.side1 === this.side2 ||
            this.side2 === this.side3 ||
            this.side1 === this.side3
        ) {
            return true;
        }

        return false;
    }

    validate() {
        return this.isIsosceles();
    }
}

class Right extends Triangle {

    constructor(side1, side2, side3) {
        super(side1, side2, side3);

        this.isValidTriangle = super.validate();
        this.isValidRight = this.validate();
    }

    isRight() {
        if (
            Math.pow(this.side1, 2) + Math.pow(this.side2, 2) === Math.pow(this.side3, 2) ||
            Math.pow(this.side1, 2) + Math.pow(this.side3, 2) === Math.pow(this.side2, 2) ||
            Math.pow(this.side2, 2) + Math.pow(this.side3, 2) === Math.pow(this.side1, 2)
        ) {
            return true;
        }

        return false;
    }

    validate() {
        return this.isRight();
    }
}

class Equilateral extends Triangle {

    constructor(side1, side2, side3) {
        super(side1, side2, side3);

        this.isValidTriangle = super.validate();
        this.isValidEquilateral = this.validate();
    }

    isEquilateral() {
        if (
            this.side1 === this.side2 &&
            this.side2 === this.side3 &&
            this.side1 === this.side3
        ) {
            return true;
        }

        return false;
    }

    validate() {
        return this.isEquilateral();
    }
}

module.exports = {
    Triangle,
    Scalene,
    Isosceles,
    Right,
    Equilateral
}
