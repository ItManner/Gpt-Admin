import request from '@/utils/request'

// 查询套餐列表
export function listPackage(query) {
  return request({
    url: '/system/package/list',
    method: 'get',
    params: query
  })
}

// 查询套餐详细
export function getPackage(id) {
  return request({
    url: '/system/package/' + id,
    method: 'get'
  })
}

// 新增套餐
export function addPackage(data) {
  return request({
    url: '/system/package',
    method: 'post',
    data: data
  })
}

// 修改套餐
export function updatePackage(data) {
  return request({
    url: '/system/package',
    method: 'put',
    data: data
  })
}

// 删除套餐
export function delPackage(id) {
  return request({
    url: '/system/package/' + id,
    method: 'delete'
  })
}
