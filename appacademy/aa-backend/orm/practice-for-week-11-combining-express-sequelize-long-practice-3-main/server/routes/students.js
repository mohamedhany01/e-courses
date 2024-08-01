// Instantiate router - DO NOT MODIFY
const express = require('express');
const router = express.Router();

// Import model(s)
const { Student, Classroom, StudentClassroom, sequelize} = require('../db/models');
const { Op } = require("sequelize");

// Extra middleware
const pagination = require("../utils/pagination");

// List
router.get('/', pagination, async (req, res, next) => {
    let errorResult = { errors: [], count: 0, pageCount: 0 };

    const { page, size } = req.query;
    const { NODE_ENV } = process.env;

    // Phase 4: Student Search Filters
    /*
        firstName filter:
            If the firstName query parameter exists, set the firstName query
                filter to find a similar match to the firstName query parameter.
            For example, if firstName query parameter is 'C', then the
                query should match with students whose firstName is 'Cam' or
                'Royce'.

        lastName filter: (similar to firstName)
            If the lastName query parameter exists, set the lastName query
                filter to find a similar match to the lastName query parameter.
            For example, if lastName query parameter is 'Al', then the
                query should match with students whose lastName has 'Alfonsi' or
                'Palazzo'.

        lefty filter:
            If the lefty query parameter is a string of 'true' or 'false', set
                the leftHanded query filter to a boolean of true or false
            If the lefty query parameter is neither of those, add an error
                message of 'Lefty should be either true or false' to
                errorResult.errors
    */
    const where = {};

    const { firstName, lastName, lefty } = req.query;

    if (firstName) {
        where.firstName = {
            [Op.like]: firstName
        }
    }

    if (lastName) {
        where.lastName = {
            [Op.like]: lastName
        }
    }

    if (lefty) {
        if (lefty === "true") {
            where.leftHanded = {
                [Op.eq]: true
            }
        } else if (lefty === "false") {
            where.leftHanded = {
                [Op.eq]: false
            }
        } else {
            errorResult.count = 0;

            errorResult.errors.push({ message: 'Lefty should be either true or false' });

            res.statusCode = 400;

            return res.json(errorResult);
        }
    }


    // Phase 2C: Handle invalid params with "Bad Request" response
    // Phase 3C: Include total student count in the response even if params were
        // invalid
        /*
            If there are elements in the errorResult.errors array, then
            return a "Bad Request" response with the errorResult as the body
            of the response.

            Ex:
                errorResult = {
                    errors: [{ message: 'Grade should be a number' }],
                    count: 267,
                    pageCount: 0
                }
        */
    if (req.errorActive) {

        errorResult.count = 0;

        errorResult.errors.push({ message: 'Requires valid page and size params' });

        res.statusCode = 400;

        return res.json(errorResult);
    }

    let result = {};

    // Phase 3A: Include total number of results returned from the query without
        // limits and offsets as a property of count on the result
        // Note: This should be a new query
    const [filteredStudents, filteredCount] = await Promise.all([
        Student.findAll({
            attributes: [
                'id',
                'firstName',
                'lastName',
                'leftHanded',
            ],
            where,
            order: [
                ['lastName'],
                ['firstName'],
            ],
            include: [
                {
                    model: Classroom,
                    attributes: ['id', 'name'],
                    through: {
                        attributes: ['grade'],
                    },
                    order: [
                        [Classroom.StudentClassroom, 'grade']
                    ],
                },
            ],
            ...req.pagination,
            // raw: true, You must disable raw here
        }),
        Student.findOne({
            attributes: [
                [sequelize.fn("COUNT", sequelize.col("*")), "count"]
            ],
            where,
            raw: true,
        })
    ]);

    result = {
        count: filteredCount.count,
        rows: filteredStudents,
        page: parseInt(page),
        pageCount: Math.ceil(filteredCount.count / parseInt(size))
    }

    // Phase 2E: Include the page number as a key of page in the response data
        // In the special case (page=0, size=0) that returns all students, set
            // page to 1
        /*
            Response should be formatted to look like this:
            {
                rows: [{ id... }] // query results,
                page: 1
            }
        */
    if (NODE_ENV === "development") {
        const [countResult, students] = await Promise.all([
            Student.findOne({
                attributes: [
                    [sequelize.fn("COUNT", sequelize.col("*")), "count"]
                ],
                raw: true,
            }),
            Student.findAll({
                raw: true,
            })
        ]);

        result = {
            count: countResult.count,
            rows: students,
            page: 1,
        };
    }

    // Phase 3B:
        // Include the total number of available pages for this query as a key
            // of pageCount in the response data
        // In the special case (page=0, size=0) that returns all students, set
            // pageCount to 1
        /*
            Response should be formatted to look like this:
            {
                count: 17 // total number of query results without pagination
                rows: [{ id... }] // query results,
                page: 2, // current page of this query
                pageCount: 10 // total number of available pages for this query
            }
        */

    res.json(result);
});

// Export class - DO NOT MODIFY
module.exports = router;