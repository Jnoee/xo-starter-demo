import request from '@/bin/utils/request'

// 查询缓存列表
export function getCacheList() {
    return request({
        url: '/entity-caches',
        method: 'GET'
    })
}

// 清空所有缓存
export function delCacheAll() {
    return request({
        url: '/entity-caches/all',
        method: 'DELETE'
    })
}

// 清理指定的实体类缓存
export function delClassCache( data ) {
    return request({
        url: '/entity-caches/classes',
        method: 'DELETE',
        data
    })
}

// 清空所有集合缓存
export function delCollectionCache() {
    return request({
        url: '/entity-caches/collection',
        method: 'DELETE'
    })
}

// 清空所有实体缓存
export function delEntityCache() {
    return request({
        url: '/entity-caches/entity',
        method: 'DELETE'
    })
}

// 清空所有查询缓存
export function delQueryCache() {
    return request({
        url: '/entity-caches/query',
        method: 'DELETE'
    })
}
