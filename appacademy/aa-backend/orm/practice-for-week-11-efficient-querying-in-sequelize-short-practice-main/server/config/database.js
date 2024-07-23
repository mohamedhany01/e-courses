module.exports = {
  development: {
    storage: process.env.DB_FILE,
    dialect: "sqlite",
    seederStorage: "sequelize",
    logQueryParameters: true,
    typeValidation: true,

    // STEP #0a: Configure Benchmarking

    // Your code here
  },
  test: {
    storage: process.env.DB_TEST_FILE,
    dialect: "sqlite",
    seederStorage: "sequelize",
    logging: false
  },
};