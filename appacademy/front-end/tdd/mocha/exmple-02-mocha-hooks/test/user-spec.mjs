import { expect } from 'chai'; // https://github.com/chaijs/chai/issues/1561

import User from '../class/user.mjs';

/*
When creating a suite of tests for a user class, you will likely need to create a new user before each test runs. You can clean up the repetition using the Mocha Hooks, before or beforeEach.

The beforeEach hook will set up code that runs before each test in the describe block while before runs once at the beginning of the block.

Similarly, there are afterEach and after hooks which can be used to clean up after each test, or after the test block runs.
*/

describe('User class', () => {

    let user;

    beforeEach(() => {
        user = new User("Foo");
    });

    it('should create new User object successfully', () => {
        expect(user).to.exist;
    });

    it('should set username on creation', () => {
        expect(user.username).to.equal("Foo");
    });
})