import request from '@/utils/request'

// 查询用户余额列表
export function listBalance(query) {
  return request({
    url: '/system/balance/list',
    method: 'get',
    params: query
  })
}

// 查询用户余额详细
export function getBalance(id) {
  return request({
    url: '/system/balance/' + id,
    method: 'get'
  })
}

// 新增用户余额
export function addBalance(data) {
  return request({
    url: '/system/balance',
    method: 'post',
    data: data
  })
}

// 修改用户余额
export function updateBalance(data) {
  return request({
    url: '/system/balance',
    method: 'put',
    data: data
  })
}

// 删除用户余额
export function delBalance(id) {
  return request({
    url: '/system/balance/' + id,
    method: 'delete'
  })
}
