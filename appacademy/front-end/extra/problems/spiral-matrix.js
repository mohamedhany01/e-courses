const spiralOrder = function(matrix) {
    const result = [];
    const subMatrix = matrix[0];


    // First row
    for (let i = 0; i < subMatrix.length - 1; i++) {
        result.push(matrix[0][i]);
    }

    // Last col
    for (let i = 0; i < matrix.length - 1; i++){
        result.push(matrix[i][subMatrix.length - 1]);
    }

    // Last row
    for (let i = subMatrix.length - 1; i > 0; i--){

        result.push(matrix[matrix.length - 1][i]);
    }

    // First col
    for (let i = matrix.length - 1; i > 0; i--){

        result.push(matrix[i][matrix.length - matrix.length]);
    }

    // Middle row
    const mid = Math.floor(matrix.length / 2);

    for (let i = 1; i < subMatrix.length - 1; i++) {
        result.push(matrix[mid][i]);
    }

    return result;
  }


  matrix = [[ 1, 2, 3],
            [ 4, 5, 6],
            [ 7, 8, 9]]

  console.log(spiralOrder(matrix)); // [1,2,3,6,9,8,7,4,5]

  matrix = [[1, 2, 3, 4],
            [5, 6, 7, 8],
            [9,10,11,12]]


  console.log(spiralOrder(matrix)); // [1,2,3,4,8,12,11,10,9,5,6,7]
