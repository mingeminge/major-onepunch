import request from '../utils/request'

export function findById(id) {
  return request({
    url: '/authority/' + id,
    method: 'get',
  })
}

export function findAll() {
  return request({
    url: '/authority/list', method: 'get'
  })
}

export function save(params) {
  return request({
    url: '/authority/save',
    method: 'post',
    data: params
  })
}

export function update(params) {
  return request({
    url: '/authority/update',
    method: 'post',
    data: params
  })
}

export function deleteById(id) {
  return request({
    url: '/authority/delete/' + id,
    method: 'post'
  })
}
