import request from '@/bin/utils/request'


// 查询用户列表
export function getUserList( {pageNum, pageSize, orderBy, sort, keyword} = data ) {
  return request({
      url: '/users?pageNum='+ pageNum + '&pageSize='+ pageSize + '&orderBy='+ orderBy +'&sort='+ sort +'&keyword='+ keyword,
      method: 'GET'
  })
}

// 新增用户
export function addUser( {actorName, name, organId, roleId, username, ordinal} = data ) {
    return request({
        url: '/users',
        method: 'POST',
        data: {
            actorName,
            name,
            organId,
            roleId,
            username,
            ordinal
        }
    })
}

// 更新用户
export function updateUser( {id, name, ordinal, username} = data ) {
    return request({
        url: '/users',
        method: 'PUT',
        data: {
            id,
            name,
            ordinal,
            username
        }
    })
}

// 停用用户
export function disableUser(id) {
    return request({
        url: '/users/disable/' + id,
        method: 'PATCH'
    })
}

// 启用用户
export function enableUser(id) {
    return request({
        url: '/users/enable/' + id,
        method: 'PATCH'
    })
}

// 重置密码
export function resetPwd( {id, managePassword} = data ) {
    return request({
        url: '/users/reset/' + id + '?managePassword=' + managePassword,
        method: 'PATCH'
    })
}

// 获取用户信息
export function getUserInfo(id) {
    return request({
        url: '/users/' + id,
        method: 'GET'
    })
}

// 查询职务列表
export function getActorList(userId) {
    return request({
        url: '/users/'+ userId +'/actors',
        method: 'GET',
    })
}

// 设置默认职务
export function setDefaultActor(id) {
    return request({
        url: '/users/default-actor/' + id,
        method: 'PATCH'
    })
}

// 新增职务
export function addActor({ userId, name, organId, roleId } = data) {
    return request({
        url: '/users/'+ userId +'/actors',
        method: 'POST',
        data: {
            name,
            organId,
            roleId
        }
    })
}

// 更新职务
export function updateActor({ userId, id, name, organId, roleId } = data) {
    return request({
        url: '/users/'+ userId +'/actors',
        method: 'PUT',
        data: {
            id,
            name,
            organId,
            roleId
        }
    })
}

// 删除职务
export function delActor({ userId, id } = data) {
    return request({
        url: '/users/'+ userId +'/actors/'+ id,
        method: 'DELETE'
    })
}
