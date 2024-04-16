const chai = require("chai");
const spies = require('chai-spies');
chai.use(spies);
const { expect } = chai;


const Person = require("../problems/person");

describe("Person class", () => {
    let person;

    beforeEach(() => {
        person = new Person("Foo", 120);
    });

    it("should create a new person", () => {
        expect(person).be.exist;
    });

    it("should create a new person with a name and age", () => {
        expect(person.name).to.equal("Foo");
        expect(person.age).to.equal(120);
    });

    it("should return a hello message", () => {
        const expected = "Hello! I am Foo";
        const actual = person.sayHello();

        expect(expected).to.equal(actual);
    });

    it("should return a person visit another person", () => {
        const person2 = new Person("Buz", 200);

        const expected = "Foo visited Buz";
        const actual = person.visit(person2);

        expect(expected).to.equal(actual);
    });

    context("switchVisit() method", () => {

        it("should return a switched person visit another person", () => {
            const person2 = new Person("Buz", 200);

            const expected = "Buz visited Foo";
            const actual = person.switchVisit(person2);

            expect(expected).to.equal(actual);
        });

        it("should call visit method", () => {
            const person2 = new Person("Buz", 200);

            const spy = chai.spy.on(person, "switchVisit");

            person.switchVisit(person2);

            expect(spy).to.have.been.called.once;
        });

        it("should call visit method with specific arg(s) inside switchVisit", () => {
            const person2 = new Person("Buz", 200);

            const spy = chai.spy.on(person2, "visit");

            person.switchVisit(person2);

            expect(spy).to.have.been.called.with(person);
        });
    });

    context("update() method", () => {

        it("A- should throw TypeError if it is invalid object (null)", () => {
            const obj = null;

            let result = () => person.update(obj);

            expect(result).to.throw(TypeError);

        });

        it("A- should throw TypeError if it is invalid object (empty object)", () => {
            const obj = {};

            result = () => person.update(obj);

            expect(result).to.throw(TypeError);
        });

        it("B- should update name and age", () => {
            const expected = {
                name: "John Doe",
                age: 100
            }

            person.update(expected);

            expect(person).deep.equal(expected);
        });


        it("D- shouldn't update name and age", () => {
            const incompleteObject = { name: "John Doe" };

            let result = () => person.update(incompleteObject);

            expect(result).to.throw(TypeError);
        });
    });

    context("tryUpdate() method", () => {

        it("A- should return true when updating the object successfully", () => {
            const obj = {
                name: "John Doe",
                age: 100
            }

            const actual = person.tryUpdate(obj);

            expect(actual).to.be.true;
            expect(person.name).to.equal(obj.name);
            expect(person.age).to.equal(obj.age);

        });

        it("A- should call update on returning true", () => {
            const obj = {
                name: "John Doe",
                age: 100
            }

            const spy = chai.spy.on(person, "update");

            person.tryUpdate(obj);

            expect(spy).to.have.been.called.once;

        });

        it("A- should call update with a specific arg(s) and returning true", () => {
            const obj = {
                name: "John Doe",
                age: 100
            }

            const spy = chai.spy.on(person, "update");

            person.tryUpdate(obj);

            expect(spy).to.have.been.called.with(obj);
        });

        it("B- should return false when not updating the object successfully", () => {
            const obj = {
                name: "John Doe",
            }

            const actual = person.tryUpdate(obj);

            expect(actual).to.be.false;
        });
    });

    context("greetAll() static method", () => {

        it("should greet three persons", () => {
            const persons = [
                new Person("Foo", 101),
                new Person("Buz", 102),
                new Person("Bar", 103),
            ];

            const actual = Person.greetAll(persons);

            expect(actual).to.be.an("array");
            expect(actual.length).equal(3);
        });

        it("should call sayHello() three times", () => {
            const persons = [
                new Person("Foo", 101),
                new Person("Buz", 102),
                new Person("Bar", 103),
            ];

            // Attach a spy to each person
            const spies = persons.map(person => chai.spy.on(person, "sayHello"));

            Person.greetAll(persons);

            // Test each spy individually
            spies.forEach(spy => {
                expect(spy).to.have.been.called.once;
            });
        });

        it("should return null in greet all if not an array", () => {
            const persons = 1;

            const actual = Person.greetAll(persons);

            expect(actual).equal(null);
        });
    })
});