module.exports = function pagination(req, res, next) {

    // Phase 2A: Use query params for page & size
    let { page, size } = req.query;
    const { NODE_ENV } = process.env;

    // Phase 2B: Calculate limit and offset
    // Phase 2B (optional): Special case to return all students (page=0, size=0)
    // Phase 2B: Add an error message to errorResult.errors of
        // 'Requires valid page and size params' when page or size is invalid
    const isValidQuery = parseInt(page) && parseInt(size);
    const isDevMode = (NODE_ENV === "development");

    if (!isValidQuery && !isDevMode) {

        req.errorActive = true;

        return next();
    }

    const [defaultPage, defaultSize] = [1, 10];

    let pageNum = parseInt(page) ? page : defaultPage;
    let sizeNum = parseInt(size) ? size : defaultSize;

    req.pagination = {
        limit: sizeNum,
        offset: sizeNum * (pageNum - 1),
    };

    return next();
}
