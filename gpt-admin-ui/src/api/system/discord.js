import request from '@/utils/request'

// 查询Discord 频道列表
export function listDiscord(query) {
  return request({
    url: '/system/discord/list',
    method: 'get',
    params: query
  })
}

// 查询Discord 频道详细
export function getDiscord(id) {
  return request({
    url: '/system/discord/' + id,
    method: 'get'
  })
}

// 新增Discord 频道
export function addDiscord(data) {
  return request({
    url: '/system/discord',
    method: 'post',
    data: data
  })
}

// 修改Discord 频道
export function updateDiscord(data) {
  return request({
    url: '/system/discord',
    method: 'put',
    data: data
  })
}

// 删除Discord 频道
export function delDiscord(id) {
  return request({
    url: '/system/discord/' + id,
    method: 'delete'
  })
}
