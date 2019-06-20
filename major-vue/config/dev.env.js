'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://localhost:8080"',
  IMG_URL: '"http://localhost:8081/static/images/headerImg/"'
/*  BASE_API: '"http://47.105.230.85:8080"',
  IMG_URL: '"http://47.105.230.85:9090/"'*/
});
