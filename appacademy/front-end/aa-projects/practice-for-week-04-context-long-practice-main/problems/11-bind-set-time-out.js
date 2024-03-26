function boundFuncTimer(obj, func, delay) {
  return setTimeout(func.bind(obj), delay);
}

/*****************************************************************************/
/***************** DO NOT MODIFY ANYTHING UNDER THIS LINE ********************/

module.exports = boundFuncTimer;