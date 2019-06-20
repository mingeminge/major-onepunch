import request from '../utils/request'

export function findByUsername(username) {
  return request({
    url: '/personal/findByUsername/' + username,
    method: 'get'
  })
}

export function updatePass(param) {
  return request({
    url: '/personal/updatePassword',
    method: 'post',
    data: param
  })
}

export function updateInfo(info) {
  return request({
    url: '/personal/updateInfo',
    method: 'post',
    data: info
  })
}

export function updateImg(params) {
  return request({
    url: '/personal/updateImg',
    method: 'post',
    data: params
  })
}

export function getImgList(params) {
  return request({
    url: '/personal/imgList',
    method: 'get',
    params:params
  })
}
