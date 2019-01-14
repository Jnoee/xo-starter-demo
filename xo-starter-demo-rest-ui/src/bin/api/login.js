import request from '@/bin/utils/request'

// 登录
export function login(username, password) {
  return request({
    url: '/login',
    method: 'POST',
    data: {
      username,
      password
    }
  })
}

// 退出登录
export function logout() {
  return request({
    url: '/logout',
    method: 'DELETE'
  })
}

// 获取所有权限列表
export function getPrivilegs() {
  return request({
    url: '/privilegs',
    method: 'GET'
  })
}
