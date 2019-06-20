import request from '../utils/request'

export function getInfo(username) {
  return request({
    url: '/visitLog/info/' + username,
    method: 'get'
  })
}

export function getLoginChart(username) {
  return request({
    url: '/visitLog/getLoginChart/' + username,
    method: 'get'
  })
}
