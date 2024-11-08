const router = require('express').Router();
const asyncHandler = require('express-async-handler');
const { setTokenCookie, restoreUser, requireAuth } = require('../../utils/auth.js');
const { User } = require('../../db/models');

const sessionRouter = require('./session.js');
const usersRouter = require('./users.js');

router.use('/session', sessionRouter);
router.use('/users', usersRouter);

router.post('/test', function (req, res) {
    res.json({ requestBody: req.body });
});

/*
    Once you are satisfied with the test results, 
    you can remove all code for testing the user auth middleware routes.
*/

// GET /api/set-token-cookie
router.get('/set-token-cookie', asyncHandler(async (_req, res) => {
    const user = await User.findOne({
        where: {
            username: 'Demo-lition'
        }
    });
    setTokenCookie(res, user);
    return res.json({ user });
}));

// GET /api/restore-user
router.get(
    '/restore-user',
    restoreUser,
    (req, res) => {
        return res.json(req.user);
    }
);

// GET /api/require-auth
router.get(
    '/require-auth',
    requireAuth,
    (req, res) => {
        return res.json(req.user);
    }
);


module.exports = router;