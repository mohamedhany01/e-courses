// Instantiate router - DO NOT MODIFY
const express = require('express');
const router = express.Router();

// Import models - DO NOT MODIFY
const { Insect, Tree } = require('../db/models');
const { Op } = require("sequelize");

/**
 * PHASE 7 - Step A: List of all trees with insects that are near them
 *
 * Approach: Eager Loading
 *
 * Path: /trees-insects
 * Protocol: GET
 * Response: JSON array of objects
 *   - Tree properties: id, tree, location, heightFt, insects (array)
 *   - Trees ordered by the tree heightFt from tallest to shortest
 *   - Insect properties: id, name
 *   - Insects for each tree ordered alphabetically by name
 */
router.get('/trees-insects', async (req, res, next) => {
    let trees = [];

    trees = await Tree.findAll({
        attributes: ['id', 'tree', 'location', 'heightFt'],
        include: [{
            attributes: ['id', 'name'],
            model: Insect,
            through: {
                attributes: []
            },
            required: true
        }],
        order: [
            ['heightFt', 'DESC'],
            [Insect, 'name']
        ]
    });

    res.json(trees);
});

/**
 * PHASE 7 - Step B: List of all insects with the trees they are near
 *
 * Approach: Lazy Loading
 *
 * Path: /insects-trees
 * Protocol: GET
 * Response: JSON array of objects
 *   - Insect properties: id, name, trees (array)
 *   - Insects for each tree ordered alphabetically by name
 *   - Tree properties: id, tree
 *   - Trees ordered alphabetically by tree
 */
router.get('/insects-trees', async (req, res, next) => {
    let payload = [];

    const insects = await Insect.findAll({
        attributes: ['id', 'name', 'description'],
        order: [['name']],
    });
    for (let i = 0; i < insects.length; i++) {
        const insect = insects[i];
        payload.push({
            id: insect.id,
            name: insect.name,
            description: insect.description,
            trees: await insect.getTrees({
                attributes: ['id', 'tree'],
                order: ['tree'],
                joinTableAttributes: []
            })
        });
    }

    res.json(payload);
});


/**
 * ADVANCED PHASE 3 - Record information on an insect found near a tree
 *
 * Path: /associate-tree-insect
 * Protocol: POST
 * Parameters: None
 * Request Body: JSON Object
 *   - Property: tree Object
 *     with id, name, location, height, size
 *   - Property: insect Object
 *     with id, name, description, fact, territory, millimeters
 * Response: JSON Object
 *   - Property: status
 *     - Value: success
 *   - Property: message
 *     - Value: Successfully recorded information
 *   - Property: data
 *     - Value: object (the new tree)
 * Expected Behaviors:
 *   - If tree.id is provided, then look for it, otherwise create a new tree
 *   - If insect.id is provided, then look for it, otherwise create a new insect
 *   - Relate the tree to the insect
 * Error Handling: Friendly messages for known errors
 *   - Association already exists between {tree.tree} and {insect.name}
 *   - Could not create association (use details for specific reason)
 *   - (Any others you think of)
 */
router.post('/associate-tree-insect', async (req, res, next) => {
    try {
        const error = new Error();

        const { tree, insect } = req.body;
        let foundInsect, foundTree
        if (!tree) {
            error.message = 'tree missing in request';
            throw error;
        }
        if (!insect) {
            error.message = 'insect missing in request';
            throw error;
        }

        if (tree) {
            if (tree.id) {
                foundTree = await Tree.findByPk(tree.id);
                if (!foundTree) {
                    error.message = `Tree ${tree.id} not found`
                }
            } else {
                // no tree id
                foundTree = await Tree.build({
                    tree: tree.name,
                    location: tree.location,
                    heightFt: tree.height,
                    groundCircumferenceFt: tree.size
                });
                await foundTree.save();
            }
        }

        if (insect) {
            if (insect.id) {
                foundInsect = await Insect.findByPk(insect.id);
                if (!foundInsect) {
                    error.message = `Insect ${insect.id} not found`;
                    throw error;
                }
            } else {
                foundInsect = await Insect.build(insect);
                await foundInsect.save();
            }
        }

        if (tree && insect) {
            if (await foundInsect.hasTree(foundTree) && await foundTree.hasInsect(foundInsect)) {
                error.message = `Association already exists between ${foundTree.tree} and ${foundInsect.name}`;
                throw error;
            } else {
                await foundInsect.addTree(foundTree);
                res.json({
                    status: 'success',
                    message: 'Successfully created association',
                    data: { tree, insect }
                })
            }
        }
    } catch (error) {
        next(error)
    }
})

// Export class - DO NOT MODIFY
module.exports = router;