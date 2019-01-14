import request from '@/bin/utils/request'

// 获取机构树信息
export function getOrgansTree() {
  return request({
    url: '/organs',
    method: 'GET'
  })
}

// 获取机构信息
export function getOrgans(id) {
  return request({
    url: '/organs/' + id,
    method: 'GET'
  })
}

// 新增机构
export function addOrgan({ name, ordinal, parentId } = data) {
  return request({
    url: '/organs',
    method: 'POST',
    data: {
      name,
      ordinal,
      parentId
    }
  })
}

// 更新机构
export function updateOrgan({ id, name, ordinal, parentId = '' } = data) {
  return request({
    url: '/organs',
    method: 'PUT',
    data: {
      id,
      name,
      ordinal,
      parentId
    }
  })
}

// 停用机构
export function disableOrgans(id) {
  return request({
    url: '/organs/disable/' + id,
    method: 'PATCH',
  })
}

// 启用机构
export function enableOrgans(id) {
  return request({
    url: '/organs/enable/' + id,
    method: 'PATCH',
  })
}