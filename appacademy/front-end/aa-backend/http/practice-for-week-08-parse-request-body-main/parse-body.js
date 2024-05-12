function firstStep(input) {
  return input.split("&");
}

function secondStep(input) {
  return input.map((entry) => entry.split("=")).map((twoPairs) => [twoPairs[0], twoPairs[1]]);
}

function thirdStep(input) {
  // Remove "+"
  return input.map((twoPairs) => [twoPairs[0], twoPairs[1].split("+").join(" ")]);
}

function fourthStep(input) {

  // Decode percent-encoding
  // https://developer.mozilla.org/en-US/docs/Glossary/percent-encoding
  return input.map((twoPairs) => [twoPairs[0], decodeURIComponent(twoPairs[1])]);
}

function fifthStep(input) {
  return input.reduce((obj, arr) => ({ ...obj, [arr[0]]: arr[1] }), {});
}

function parseBody(str) {
  if (!str) return;

  return [firstStep, secondStep, thirdStep, fourthStep, fifthStep].reduce((result, fun) => fun(result), str);
}

/******************************************************************************/
/******************* DO NOT CHANGE THE CODE BELOW THIS LINE *******************/

module.exports = {
  firstStep,
  secondStep,
  thirdStep,
  fourthStep,
  fifthStep,
  parseBody
};