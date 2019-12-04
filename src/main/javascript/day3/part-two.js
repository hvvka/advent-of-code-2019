class Point {
    constructor(x, y, steps) {
        this.x = x;
        this.y = y;
        this.steps = steps;
    }

    calculateManhatanDistance() {
        return Math.abs(this.x) + Math.abs(this.y);
    }
}

const commands = {
    "L": (position, s) => new Point(position.x - 1, position.y, s),
    "U": (position, s) => new Point(position.x, position.y + 1, s),
    "R": (position, s) => new Point(position.x + 1, position.y, s),
    "D": (position, s) => new Point(position.x, position.y - 1, s),
};

let map = [];
let cross = [];
let center = new Point(0, 0, 0);
map.push(center);

let steps = 0;
const addPoints = (callback) => move => {
    let reps = Number(move.substr(1));
    while (reps--) {
        steps++;
        let point = commands[move[0]](map.slice(-1)[0], steps);
        callback(point);
        map.push(point);
    }
};

let input1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(",");
let input2 = "U62,R66,U55,R34,D71,R55,D58,R83".split(",");

input1.forEach(addPoints(() => {
}));
map.push(center);
steps = 0;
let hasPoint = (point) => map.find(p => p.x === point.x && p.y === point.y);
input2.forEach(addPoints((point) => {
    if (hasPoint(point)) {
        let points = map.filter(p => p.x === point.x && p.y === point.y);
        cross.push({...point, steps: points.map(p => p.steps).reduce((a, b) => a + b, 0) + point.steps});
    }
}));

let totalSteps = cross.map(p => p.steps);
console.log(Math.min(...totalSteps)); // 13190
