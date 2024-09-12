const express = require('express');
const router = express.Router();

const apiRouter = require('./api');
router.use('/api', apiRouter);


router.get('/hello/world', function (req, res) {
    const token = req.csrfToken();

    console.log(token);
    
    res.cookie('XSRF-TOKEN', token);
    res.send('Hello World!');
});

// Add a XSRF-TOKEN cookie
/*
    This route should not be available in production, 
    but it will not be exclusive to the production application 
    until you implement the frontend of the application later. 
    So for now, it will remain available to both the development 
    and production environments.
*/
router.get("/api/csrf/restore", (req, res) => {
    const csrfToken = req.csrfToken();
    res.cookie("XSRF-TOKEN", csrfToken);
    res.status(200).json({
        'XSRF-Token': csrfToken
    });
});

module.exports = router;