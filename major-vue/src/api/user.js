import request from '../utils/request'

export function login(param) {
  return request({
    url: '/login',
    method: 'post',
    data: param
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function findById(id) {
  return request({
    url: '/user/findById/' + id,
    method: 'get'
  })
}

export function list(param) {
  return request({
    url: '/user/list',
    method: 'get',
    params: param
  })
}

export function update(param) {
  return request({
    url: '/user/update',
    method: 'post',
    data: param
  })
}

export function save(param) {
  return request({
    url: '/user/save',
    method: 'post',
    data: param
  })
}

export function deleteById(id) {
  return request({
    url: '/user/delete/' + id,
    method: 'post'
  })
}

export function userInfo() {
  return request({
    url: '/user/userInfo/',
    method: 'get'
  })
}

export function getRoleList() {
  return request({
    url: '/user/roleList',
    method: 'get'
  })
}

