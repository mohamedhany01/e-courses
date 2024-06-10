/*

Fix the function `shouldRecycle` that determines if the item passed in can
or cannot be recycled.

If an item is plastic then it can be recycled and should return `Recycle Me!`
UNLESS its color is black. Black plastics should return `Currently, cannot be
recycled.`

If an item is made of aluminum or paper then it can be recycled and should
return `Recycle Me!`

*/


function shouldRecycle(item) {
  const isPlasticNotBlack = item.plastic && (item.color !== 'black');
  const isPlasticBlack = item.plastic && item.color === 'black';

  if (item.aluminum || item.paper || isPlasticNotBlack) {

    return 'Recycle Me!'
  } else if (isPlasticBlack) {

    return 'Currently, cannot be recycled.'
  } else {

    return 'Cannot be recycled'
  }
}


const waterBottle = {
  plastic: true,
  color: 'clear',
  aluminum: false,
  paper: false
};

console.log(shouldRecycle(waterBottle)); // 'Recycle Me!'

const tomatoCan = {
  plastic: false,
  color: 'red',
  aluminum: true,
  paper: false
};

console.log(shouldRecycle(tomatoCan)); // 'Recycle Me!'

const saladContainer = {
  plastic: true,
  color: 'black',
  aluminum: false,
  paper: false
};

console.log(shouldRecycle(saladContainer)); // 'Currently, cannot be recycled.'

const styrofoamContainer = {
  plastic: false,
  color: 'black',
  aluminum: false,
  paper: false
};

console.log(shouldRecycle(styrofoamContainer)); // 'Cannot be recycled.'
