import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/systemLog/findAll',
    method: 'get',
    params: params
  })
}

export function deleteLog(id) {
  return request({
    url: '/systemLog/deleteLog/' + id,
    method: 'post'
  })
}
