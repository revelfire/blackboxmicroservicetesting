'use strict';

const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

var express = require('express');
var app = express();
var fs = require("fs");

app.set('view engine', 'html');

app.get('/api/order/burger', (req, res) => {
    console.log(`you asked for burger with:${req.query.with}`);
    res.end(burger_response.replace('{with}', req.query.with));
});

app.get('/api/window/', (req, res) => {
    console.log(`you pulled up to the window and got your food`);
	res.end(window_response);
});

var server = app.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
    app.emit("serverStarted");
});

let burger_response = `
[{
  "type": "royale",
  "with": "{with}",
  "price": "3.99"
}]
`;

let window_response = `
[{
  "servant": "Johnny P",
  "time_taken": "45000",
  "burger_correct": true
}]
`;