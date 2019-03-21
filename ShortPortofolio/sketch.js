var bgColor;
var s;
var cond = false;
var scl = 20;
var curr_score = 0;
var scoreBoard_curr;
var scoreBoard_best;
var food;


function setup() {
    scoreBoard_best = document.getElementById("best-score");
    scoreBoard_curr = document.getElementById("score");
    createCanvas(100 * 15, 60 * 10);
    s = new Snake();
    frameRate(10);
    pickLocation();
    bgColor = color(10, 20, 30);
}


function Snake() {
    this.x = 0;
    this.y = 0;
    this.xspeed = 1;
    this.yspeed = 0;
    this.total = 0;
    this.tail = [];

    this.update = function() {
        if (this.total === this.tail.length) {
            for (var i = 0; i < this.tail.length - 1; i++) {
                this.tail[i] = this.tail[i + 1];
            }
        }
        this.tail[this.total - 1] = createVector(this.x, this.y);

        this.x = this.x + this.xspeed * scl;
        this.y = this.y + this.yspeed * scl;

        this.x = constrain(this.x, 0, width - scl);
        this.y = constrain(this.y, 0, height - scl);
    };
    this.die = function() {
        for (var i = 0; i < this.tail.length; i++) {
            var pos = this.tail[i];
            var d = dist(this.x, this.y, pos.x, pos.y);
            if (d < 1) {
                if (scoreBoard_best.innerHTML < curr_score) {
                    scoreBoard_best.innerHTML = curr_score;
                }

                scoreBoard_curr.innerHTML = 0;
                curr_score = 0;
                this.total = 0;
                this.tail = [];

            }
        }
    };
    this.eat = function(pos) {
        var d = dist(this.x, this.y, pos.x, pos.y);
        if (d < 1) {
            this.total++;
            curr_score++;
            scoreBoard_curr.innerHTML = this.total;
            return true;
        } else {
            return false;
        }
    };

    this.direction = function(x, y) {
        this.xspeed = x;
        this.yspeed = y;
    };
    this.show = function() {
        var r = random(255);
        var g = random(255);
        var a = random(255);
        fill(r, g, a);
        for (var i = 0; i < this.tail.length; i++) {
            rect(this.tail[i].x, this.tail[i].y, scl, scl);
        }
        fill(0, 255, 100);
        rect(this.x, this.y, scl, scl);
    };
}

function keyPressed() {
    if (keyCode === UP_ARROW) {
        s.direction(0, -1);
    } else if (keyCode === DOWN_ARROW) {
        s.direction(0, 1);
    } else if (keyCode === LEFT_ARROW) {
        s.direction(-1, 0);
    } else if (keyCode === RIGHT_ARROW) {
        s.direction(1, 0);
    }
}


function pickLocation() {
    var cols = floor(width / scl);
    var rows = floor(height / scl);
    food = createVector(floor(random(cols)), floor(random(rows)));
    food.mult(scl);
}


function draw() {
    background(bgColor);
    if (s.eat(food)) {
        pickLocation();
    }
    s.die();
    s.update();
    s.show();

    fill(random(255), random(255), random(255));
    rect(food.x, food.y, scl, scl);
}