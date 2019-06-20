import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/image/list',
    method: 'get',
    params: params
  })
}

export function updateImg(params) {
  return request({
    url: '/image/update',
    method: 'post',
    data: params
  })
}

export function deleteImg(params) {
  return request({
    url: '/image/' + params,
    method: 'post'
  })
}

export function save(params) {
  return request({
    url: '/image/save',
    method: 'post',
    data: params
  })
}
