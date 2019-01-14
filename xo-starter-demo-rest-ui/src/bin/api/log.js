import request from '@/bin/utils/request'

// 获取日志列表
export function getLogList({ keyword, orderBy, pageNum, pageSize, sort } = data) {
    return request({
        url: '/logs?keyword='+ keyword +'&orderBy='+ orderBy +'&pageNum='+ pageNum +'&pageSize='+ pageSize +'&sort='+ sort,
        method: 'GET'
    })
}

// 获取详细日志
export function getLogDetails(id) {
    return request({
        url: '/logs/' + id,
        method: 'GET'
    })
}
