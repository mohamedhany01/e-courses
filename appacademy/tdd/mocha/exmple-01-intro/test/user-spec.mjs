import { expect } from 'chai'; // https://github.com/chaijs/chai/issues/1561

import User from '../class/user.mjs';

/*
describe is a group of tests for the User class. it tests a single spec and checks if a User can be created successfully and does this by creating a new user, then expect is an assertion that checks to see if the new user exists.

describe and it come from the testing framework, Mocha, while expect comes from the assertion library, Chai. Note that the Mocha functions literally frame the test assertions.

Check out the Chai docs for a list of all the ways to test code assertions with expect
*/

describe('User class', () => {
    it('should create new User object successfully', () => {

        const user = new User();

        expect(user).to.exist;
    });

    it('should set username on creation', () => {
        const user = new User("Foo");

        expect(user.username).to.equal("Foo");
    });
})