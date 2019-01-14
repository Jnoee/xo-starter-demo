import request from '@/bin/utils/request'

// 查询职务列表
export function getActorList() {
    return request({
        url: '/person/actors',
        method: 'GET',
    })
}

// 切换职务
export function toggleActor(actorId) {
    return request({
        url: '/person/actor-switch?actorId=' + actorId,
        method: 'PATCH'
    })
}

// 修改个人密码
export function changePwd( {oldPwd, newPwd, confirmPwd} = data ) {
    return request({
        url: '/person/change-pwd',
        method: 'PATCH',
        data: {
            oldPwd,
            newPwd,
            confirmPwd
        }
    })
}