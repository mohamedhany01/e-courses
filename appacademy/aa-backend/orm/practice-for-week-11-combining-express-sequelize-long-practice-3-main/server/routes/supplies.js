// Instantiate router - DO NOT MODIFY
const express = require('express');
const router = express.Router();

// Import model(s)
const { Supply, Classroom, Student, StudentClassroom, sequelize } = require('../db/models');

// List of supplies by category
router.get('/category/:categoryName', async (req, res, next) => {

    const { categoryName } = req.params;
    const query = {};

    // Phase 1C:
        // Find all supplies by category name
        // Order results by supply's name then handed
        // Return the found supplies as the response body
    // Phase 8A:
        // Include Classroom in the supplies query results
        // Order nested classroom results by name first then by supply name
    const where = {};

    if (categoryName) {
        where.category = categoryName;
    }

    const order = [
        [Classroom, 'name'],
        ['name'],
        // ['handed']
    ];

    query.attributes = ['id', 'name', 'handed'],
    query.where = where;
    query.order = order;
    query.include = [
        {
            model: Classroom,
            // where: {} // Filter null Classrooms
        }
    ];

    const result = await Supply.findAll(query);

    res.json(result);
});


// Scissors Supply Calculation - Business Logic Goes Here!
router.get('/scissors/calculate', async (req, res, next) => {
    let result = {};

    // Phase 10A: Current number of scissors in all classrooms
        // result.numRightyScissors should equal the total number of all
            // right-handed "Safety Scissors" currently in all classrooms
        // result.numLeftyScissors should equal the total number of all
            // left-handed "Safety Scissors" currently in all classrooms
        // result.totalNumScissors should equal the total number of all
            // "Safety Scissors" currently in all classrooms, regardless of
            // handed-ness
    const [categoriesStatistics] = await Supply.findAll({
        attributes: [
            [sequelize.fn("SUM", sequelize.literal("CASE WHEN handed = 'right' THEN 1 ELSE 0 END")), 'numRightyScissors'],
            [sequelize.fn("SUM", sequelize.literal("CASE WHEN handed = 'left' THEN 1 ELSE 0 END")), 'numLeftyScissors'],
            [sequelize.fn("COUNT", sequelize.col("*")), 'totalNumScissors']
        ],
        where: {
            category: 'Cutting'
        },
        raw: true
    });

    // Phase 10B: Total number of right-handed and left-handed students in all
        // classrooms
        // result.numRightHandedStudents should equal the total number of
            // right-handed students in all classrooms
            // Note: This is different from the total amount of students that
                // are right-handed in the database. This is a total of all
                // right-handed students in each classroom combined. Some
                // students are enrolled in multiple classrooms, so if a
                // right-handed student was enrolled in 3 classrooms, that
                // student would contribute to 3 students in the total amount of
                // right-handed students in all classrooms.
        // result.numLeftHandedStudents should equal the total number of
            // left-handed students in all classrooms
    const [classStatistics] = await Student.findAll({
        attributes: [
            [sequelize.fn("SUM", sequelize.literal("CASE WHEN leftHanded = false THEN 1 ELSE 0 END")), 'numRightHandedStudents'],
            [sequelize.fn("SUM", sequelize.literal("CASE WHEN leftHanded = true THEN 1 ELSE 0 END")), 'numLeftHandedStudents'],
        ],
        include: [
            {
                model: StudentClassroom,
                attributes: [],
            }
        ],
        raw: true
    });

    // Phase 10C: Total number of scissors still needed for all classrooms
        // result.numRightyScissorsStillNeeded should equal the total number
            // of right-handed scissors still needed to be added to all the
            // classrooms
            // Note: This is the number of all right-handed students in all
                // classrooms subtracted by the number of right-handed scissors
                // that all the classrooms already have.
        // result.numLeftyScissorsStillNeeded should equal the total number
            // of left-handed scissors still needed to be added to all the
            // classrooms
    result = {
        ...categoriesStatistics,
        ...classStatistics,
        numRightyScissorsStillNeeded: classStatistics.numRightHandedStudents - categoriesStatistics.numRightyScissors,
        numLeftyScissorsStillNeeded: classStatistics.numLeftHandedStudents - categoriesStatistics.numLeftyScissors
    };

    res.json(result);
});

// Export class - DO NOT MODIFY
module.exports = router;