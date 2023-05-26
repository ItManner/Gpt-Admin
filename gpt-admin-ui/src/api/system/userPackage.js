import request from '@/utils/request'

// 查询套餐和用户关联列表
export function listUserPackage(query) {
  return request({
    url: '/system/userPackage/list',
    method: 'get',
    params: query
  })
}

// 查询套餐和用户关联详细
export function getUserPackage(id) {
  return request({
    url: '/system/userPackage/' + id,
    method: 'get'
  })
}

// 新增套餐和用户关联
export function addUserPackage(data) {
  return request({
    url: '/system/userPackage',
    method: 'post',
    data: data
  })
}

// 修改套餐和用户关联
export function updateUserPackage(data) {
  return request({
    url: '/system/userPackage',
    method: 'put',
    data: data
  })
}

// 删除套餐和用户关联
export function delUserPackage(id) {
  return request({
    url: '/system/userPackage/' + id,
    method: 'delete'
  })
}
