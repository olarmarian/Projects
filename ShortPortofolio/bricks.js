var INITIAL_BALL_SPEED = 4;
var BALL_RADIUS = 20;

var PADDLE_WIDTH = 100;
var PADDLE_HEIGHT = 10;

var playerPosition;
var playerVelocity;
var playerScore;
var playerLifes;

var ball, ballVelocity;

var bricks = [];
var BRICK_WIDTH = 60;
var BRICK_HEIGHT = 30;
var NR_BRICKS_INLINE = 5;
var NR_COLUMNS_BRICKS = 8;

function Brick(x, y) {
    this.x = x;
    this.y = y;
    this.width = BRICK_WIDTH;
    this.height = BRICK_HEIGHT;
    this.destroyedBrick = false;

    this.show = function() {
        fill(255);
        rect(this.x, this.y, this.width, this.height);
    };
}


function setup() {
    createCanvas(600, 700);

    /* player attibutes */
    playerPosition = height / 2 - 50;
    playerVelocity = playerScore = 0;
    playerLifes = 3;

    /* ball attributes */
    ball = createVector(width / 2, height / 2);
    ballVelocity = createVector(random(-1, 1), random(-1, 1));
    ballVelocity.setMag(INITIAL_BALL_SPEED);

    /* text */
    textAlign(RIGHT);
    textSize(20);
    fill(255);

    /*bricks */
    var x_brick = 25;
    var y_brick = BRICK_HEIGHT + 20;
    for (var i = 0; i < NR_BRICKS_INLINE; i++) {
        bricks[i] = [];
        for (var j = 0; j < NR_COLUMNS_BRICKS; j++) {
            bricks[i][j] = new Brick(x_brick, y_brick);
            x_brick = x_brick + 10 + BRICK_WIDTH;
        }
        x_brick = 25;
        y_brick += 50;
    }
}

function draw() {
    background(100, 100, 100);

    /* draw paddle */
    rect(playerPosition, height - (PADDLE_HEIGHT * 2), PADDLE_WIDTH, PADDLE_HEIGHT);

    /* draw ball */
    ellipse(ball.x, ball.y, BALL_RADIUS);

    /* draw scoreboard */
    text("Score: " + playerScore, width / 5, 30);
    text("Lifes: " + playerLifes, width - width / 10, 30);

    /* geometry for paddle and ball */
    handlePaddles();
    handleBall();

    /*draw bricks */
    for (var i = 0; i < NR_BRICKS_INLINE; i++) {
        for (var j = 0; j < NR_COLUMNS_BRICKS; j++) {
            if (bricks[i][j].destroyedBrick === false) {
                bricks[i][j].show();
            }
        }
    }
}

function reset() {
    ballVelocity.setMag(INITIAL_BALL_SPEED);
    ball = createVector(width / 2, height / 2);
}

function handleBall() {
    ball.x += ballVelocity.x;
    ball.y += ballVelocity.y;

    /*top collision */
    if (ball.y < 0) {
        ballVelocity.y *= -1;
    }
    /*right && left collision */
    if (ball.x < 0 || ball.x > width) {
        ballVelocity.x *= -1;
    }

    /*paddle collision */
    if (ball.y > height - (PADDLE_HEIGHT * 3)) {
        if (ball.y > height) {
            playerLifes--;
            reset();
            return;
        }
        if (ball.x > playerPosition && ball.x < playerPosition + PADDLE_WIDTH) {
            if (ballVelocity.y > 0) {
                if (ball.x > playerPosition && ball.x < playerPosition + (PADDLE_WIDTH / 2)) {
                    ballVelocity.x *= -1;
                }
                if (ball.x > playerPosition && ball.x < playerPosition + PADDLE_WIDTH - (PADDLE_WIDTH / 2)) {
                    ballVelocity.x *= -1;
                }
                ballVelocity.y *= -1;
            } else {

                ballVelocity.y *= -1;
            }
            ballVelocity.mult(random(1, 1.2));
        }
    }

    /*brick collision */
    for (var i = 0; i < NR_BRICKS_INLINE; i++) {
        for (var j = 0; j < NR_COLUMNS_BRICKS; j++) {
            if (ball.x > bricks[i][j].x && ball.x < bricks[i][j].x + bricks[i][j].width) {
                if (((ball.y < bricks[i][j].y + BALL_RADIUS + BRICK_HEIGHT) || (ball.y < bricks[i][j].y + BRICK_HEIGHT)) && bricks[i][j].destroyedBrick === false) {
                    ballVelocity.y *= -1;
                    bricks[i][j].destroyedBrick = true;
                }
                if (((ball.x < bricks[i][j].x + BALL_RADIUS) || (ball.x > bricks[i][j].x + BRICK_WIDTH)) && (ball.y < bricks[i][j].y + BALL_RADIUS + BRICK_HEIGHT) && bricks[i][j].destroyedBrick === false) {
                    ballVelocity.x *= -1;
                    bricks[i][j].destroyedBrick = true;
                }
            }
        }
    }
}

function handlePaddles() {
    /* player controls*/
    if (keyIsDown(LEFT_ARROW)) {
        /*move left */

        playerVelocity -= 5;
    } else if (keyIsDown(RIGHT_ARROW)) {
        /*move left */

        playerVelocity += 5;
    }

    /* change position */
    playerPosition += playerVelocity;

    /* friction */
    playerVelocity *= 0.4;

    /*costrain paddle */
    playerPosition = constrain(playerPosition, 0, width - PADDLE_WIDTH);
}