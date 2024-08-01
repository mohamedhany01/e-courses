// Instantiate router - DO NOT MODIFY
const express = require('express');
const router = express.Router();

// Import model(s)
const { Classroom, Supply, Student, StudentClassroom, sequelize } = require('../db/models');
const { Op, where } = require('sequelize');

// List of classrooms
router.get('/', async (req, res, next) => {
    let errorResult = { errors: [], count: 0, pageCount: 0 };

    // Phase 6B: Classroom Search Filters
    /*
        name filter:
            If the name query parameter exists, set the name query
                filter to find a similar match to the name query parameter.
            For example, if name query parameter is 'Ms.', then the
                query should match with classrooms whose name includes 'Ms.'

        studentLimit filter:
            If the studentLimit query parameter includes a comma
                And if the studentLimit query parameter is two numbers separated
                    by a comma, set the studentLimit query filter to be between
                    the first number (min) and the second number (max)
                But if the studentLimit query parameter is NOT two integers
                    separated by a comma, or if min is greater than max, add an
                    error message of 'Student Limit should be two integers:
                    min,max' to errorResult.errors
            If the studentLimit query parameter has no commas
                And if the studentLimit query parameter is a single integer, set
                    the studentLimit query parameter to equal the number
                But if the studentLimit query parameter is NOT an integer, add
                    an error message of 'Student Limit should be a integer' to
                    errorResult.errors 
    */
    const where = {};

    const { name, studentLimit } = req.query;

    if (name) {
        where.name = {
            [Op.substring]: name
        }
    }

    if (studentLimit) {
        const params = studentLimit.split(",");

        if (params.length === 1) {

            const validLimit = parseInt(params);

            if (!validLimit) {

                errorResult.errors.push({ message: 'Student Limit should be an integer' });

                return res.json(errorResult);
            }

            where.studentLimit = {
                [Op.eq]: validLimit,
            }

        } else {

            const [minStr, maxStr] = params;

            const [min, max] = [parseInt(minStr), parseInt(maxStr)];

            const isValidParams = (min || max);
            const isMinParamValid = min < max;
            const isParamsLengthValid = params.length <= 2;

            if (
                !isValidParams      ||
                !isMinParamValid    ||
                !isParamsLengthValid
            ) {
                errorResult.errors.push({ message: 'Student Limit should be two numbers: min,max' });

                return res.json(errorResult);
            }

            where.studentLimit = {
                [Op.between]: [min, max],
            }
        }
    }


    const classrooms = await Classroom.findAll({
        attributes: [
            'id',
            'name',
            'studentLimit',
            'createdAt',
            'updatedAt',
            [sequelize.fn('AVG', sequelize.col('StudentClassrooms.grade')), 'avgGrade'],
            [sequelize.fn('COUNT', sequelize.col('StudentClassrooms.studentId')), 'numStudents'],
        ],
        where,
        // Phase 1B: Order the Classroom search results
        order: [
            ['name']
        ],
        include: [
            {
                model: StudentClassroom,
                attributes: [],
            }
        ],
        group: 'Classroom.id', // Without group by you wont get the correct input
    });

    res.json(classrooms);
});

// Single classroom
router.get('/:id', async (req, res, next) => {

    const { id } = req.params;
    const idNum = parseInt(id);

    let classroom = await Classroom.findByPk(idNum, {
        attributes: ['id', 'name', 'studentLimit'],
        // Phase 7:
            // Include classroom supplies and order supplies by category then
                // name (both in ascending order)
            // Include students of the classroom and order students by lastName
                // then firstName (both in ascending order)
                // (Optional): No need to include the StudentClassrooms
        raw: true
    });

    if (!classroom) {
        res.status(404);
        res.send({ message: 'Classroom Not Found' });
    }



    // Phase 5: Supply and Student counts, Overloaded classroom
        // Phase 5A: Find the number of supplies the classroom has and set it as
            // a property of supplyCount on the response
        // Phase 5B: Find the number of students in the classroom and set it as
            // a property of studentCount on the response
        // Phase 5C: Calculate if the classroom is overloaded by comparing the
            // studentLimit of the classroom to the number of students in the
            // classroom
        // Optional Phase 5D: Calculate the average grade of the classroom 
    const [
        suppliesData, 
        suppliesAggregation, 
        studentsData, 
        studentsAggregation, 
        averageData,
    ] = await Promise.all([
        Supply.findAll({
            attributes: {
                exclude: ['classroomId', 'createdAt', 'updatedAt']
            },
            where: {
                classroomId: {
                    [Op.eq]: idNum
                }
            },
            order: [
                ['category'],
                ['name']
            ],
            raw: true
        }),
        Classroom.findByPk(idNum, {
            attributes: [
                [sequelize.fn("COUNT", sequelize.col("*")), "supplyCount"]
            ],
            include: [
                {
                    model: Supply
                }
            ],
            raw: true
        }),
        StudentClassroom.findAll({
            attributes: [
                'Student.id',
                'Student.firstName',
                'Student.lastName',
                'Student.leftHanded',
            ],
            include: [
                {
                    model: Student,
                    attributes: [],
                }
            ],
            where: {
                classroomId: {
                    [Op.eq]: idNum
                }
            },
            order: [
                [Student, 'firstName'],
                [Student, 'lastName']
            ],
            raw: true
        }),
        Classroom.findByPk(idNum, {
            attributes: [
                [sequelize.fn("COUNT", sequelize.col("*")), "studentCount"]
            ],
            include: [
                {
                    model: Student
                }
            ],
            raw: true
        }),
        StudentClassroom.findOne({
            attributes: [
                [sequelize.fn("AVG", sequelize.col("grade")), "avgGrade"]
            ],
            where: {
                classroomId: {
                    [Op.eq]: idNum
                }
            },
            raw: true
        }),
    ]);
    
    const aggregationResult = {
        ...classroom,
        supplyCount: suppliesAggregation.supplyCount,
        studentCount: studentsData.studentCount,
        overloaded: studentsAggregation.studentCount > classroom.studentLimit,
        avgGrade: averageData.avgGrade,
        students:studentsData,
        supplies: suppliesData
    }

    res.json(aggregationResult);
});

// Export class - DO NOT MODIFY
module.exports = router;