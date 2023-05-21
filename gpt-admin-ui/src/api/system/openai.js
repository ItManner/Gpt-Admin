import request from '@/utils/request'

// 查询openai配置列表
export function listConfig(query) {
  return request({
    url: '/system/openai/list',
    method: 'get',
    params: query
  })
}

// 查询openai配置详细
export function getConfig(id) {
  return request({
    url: '/system/openai/' + id,
    method: 'get'
  })
}

// 新增openai配置
export function addConfig(data) {
  return request({
    url: '/system/openai',
    method: 'post',
    data: data
  })
}

// 修改openai配置
export function updateConfig(data) {
  return request({
    url: '/system/openai',
    method: 'put',
    data: data
  })
}

// 删除openai配置
export function delConfig(id) {
  return request({
    url: '/system/openai/' + id,
    method: 'delete'
  })
}
