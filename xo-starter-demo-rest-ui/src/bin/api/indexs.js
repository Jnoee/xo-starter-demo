import request from '@/bin/utils/request'

// 查询索引列表
export function getIndexList() {
    return request({
        url: '/entity-indexs',
        method: 'GET'
    })
}

// 重建索引
export function rebuildIndex(data) {
    return request({
        url: '/entity-indexs/build',
        method: 'POST',
        data
    })
}