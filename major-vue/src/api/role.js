import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/role/list',
    method: 'get',
    params: params
  })
}

export function findById(id) {
  return request({
    url: '/role/' + id,
    method: 'get'
  })
}

export function save(param) {
  return request({
    url: '/role/save',
    method: 'post',
    data: param
  })
}

export function update(param) {
  return request({
    url: 'role/update',
    method: 'post',
    data: param
  })
}

export function deleteRole(id) {
  return request({
    url: '/role/delete/' + id,
    method: 'post'
  })
}

export function findAuthorityList() {
  return request({
    url: '/authority/list', method: 'get'
  })
}

export function findAuthorityById(id) {
  return request({
    url: '/role/findAuthorities/' + id,
    method: 'get'
  })
}
