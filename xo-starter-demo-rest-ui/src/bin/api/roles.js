import request from '@/bin/utils/request'

// 查询角色列表
export function getRoleList(){
    return request({
        url: '/roles',
        method: 'GET'
    })
}

// 新增角色
export function addRole( {name, privilegs = []} = data){
    return request({
        url: '/roles',
        method: 'POST',
        data: {
            name,
            privilegs
        }
    })
}

// 更新角色
export function updateRole( {id, name, privilegs = []} = data){
    return request({
        url: '/roles',
        method: 'PUT',
        data: {
            id,
            name,
            privilegs
        }
    })
}

// 获取角色信息
export function getRoleInfo(id){
    return request({
        url: '/roles/' + id,
        method: 'GET'
    })
}
