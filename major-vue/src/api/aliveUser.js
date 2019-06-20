import request from '../utils/request'

export function getOnlineUserList() {
  return request({
    url:'/onlineUser/list',
    method:'get'
  })
}

export function killOnlineUser(token) {
  return request({
    url:'/onlineUser/kill/'+token,
    method: 'post'
  })
}
