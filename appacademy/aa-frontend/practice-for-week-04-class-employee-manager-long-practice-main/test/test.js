const chai = require('chai');
const expect = chai.expect;
const spies = require('chai-spies');
chai.use(spies);

const Employee = require('../employee');
const Manager = require('../manager');

describe('The calculateBonus(multiplier) method:', () => {
    let hobbes;
    let calvin;
    let susie;
    let lily;
    let clifford;
    beforeEach(() => {
        hobbes = new Manager('Hobbes', 1000000, 'Founder');
        calvin = new Manager('Calvin', 130000, 'Director', hobbes);
        susie = new Manager('Susie', 100000, 'TA Manager', calvin);
        lily = new Employee('Lily', 90000, 'TA', susie);
        clifford = new Employee('Clifford', 90000, 'TA', susie);
    });

    describe('Manager.calculateBonus()', () => {
        it('should calculate Managers', () => {
            expect(hobbes.calculateBonus(0.05)).to.equal(70500);
            expect(calvin.calculateBonus(0.05)).to.equal(20500);
            expect(susie.calculateBonus(0.05)).to.equal(14000);
        });
    });

    describe('Employee.calculateBonus()', () => {
        it('should calculate Employees under them by a passed in multiplier', () => {
            expect(lily.calculateBonus(0.05)).to.eql(4500);
            expect(clifford.calculateBonus(0.05)).to.eql(4500);
        });
    });
});