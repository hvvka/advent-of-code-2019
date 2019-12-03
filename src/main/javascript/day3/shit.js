const printMatrix = () => {
    let out = "";
    for (let vertical = matrix.length - 1; vertical >= 0; vertical--) {
        for (let horizontal = 0; horizontal < matrix[vertical].length; horizontal++) {
            out = out + ' ' + matrix[vertical][horizontal];
        }
        out += '\n'
    }
    console.log(out);
};
const recenter = () => arrayLength / 2 - 1;

const chars = ['o', '-', '|', '+'];
const arrayLength = 16;
let x = recenter();
let y = recenter();
let matrix = Array(arrayLength).fill().map(() => Array(arrayLength).fill('.'));
matrix[x][y] = 'o';

let commands = {
    "L": (left) => {
        for (let i = x - 1; i >= x - left; i--) {
            if (chars.includes(matrix[y][i])) matrix[y][i] = 'X';
            else matrix[y][i] = '-'
        }
        x -= left;
        matrix[y][x] = '+';
    },
    "U": (up) => {
        for (let i = y + 1; i < y + up; i++) {
            if (chars.includes(matrix[i][x])) matrix[i][x] = 'X';
            else matrix[i][x] = '|'
        }
        y += up;
        matrix[y][x] = '+';
    },
    "R": (right) => {
        for (let i = x + 1; i < x + right; i++) {
            if (chars.includes(matrix[y][i])) matrix[y][i] = 'X';
            else matrix[y][i] = '-'
        }
        x += right;
        matrix[y][x] = '+';
    },
    "D": (down) => {
        for (let i = y - 1; i >= y - down; i--) {
            if (chars.includes(matrix[i][x])) matrix[i][x] = 'X';
            else matrix[i][x] = '|'
        }
        y -= down;
        matrix[y][x] = '+';
    }
};

// INPUT
let wire1 = "R8,U5,L5,D3".split(",");
let wire2 = "U7,R6,D4,L4".split(",");

wire1.forEach(move => {
    commands[move[0]](Number(move.substr(1)));
});
x = recenter();
y = recenter();
wire2.forEach(move => {
    commands[move[0]](Number(move.substr(1)));
});

let points = matrix.filter(row => row.includes('X')).map(row => {
    return [matrix.indexOf(row), row.indexOf('X')]
});

x = recenter();
y = recenter();
const calculateManhatanDistance = (row) => Math.abs(points[row][0] - y) + Math.abs(points[row][1] - x);
let manhatanDistances = points.map(p => calculateManhatanDistance(points.indexOf(p)));
points.forEach(md => console.log(md));
manhatanDistances.forEach(md => console.log(md));
printMatrix();
console.log(Math.min(...manhatanDistances));
