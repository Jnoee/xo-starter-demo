import { asyncRouterMap, constantRouterMap } from '@/router'




 // 通过后台所返回的 权限列表， 与路由表中的 meta.privilegs 匹配，返回对应的路由信息

  

/**
 * 通过 meta.privilegs 判断是否与当前用户权限匹配
 * @param privilegs
 * @param route
 */
function hasPermission(privilegs, route) {
  if (route.meta && route.meta.privilegs) {
    return privilegs.some(privileg => route.meta.privilegs.includes(privileg))
  } else {
    return true
  }
}


/**
 * 递归过滤异步路由表，返回符合用户所有权限的路由表
 * @param routes
 * @param privilegs
 */
function filterRouter(routes, privilegs) {
  const res = []
  routes.map(route => {
    console.log()
    const tmp = { ...route }
    if (hasPermission(privilegs, tmp)) {
      if (tmp.children) {
        tmp.children = filterRouter(tmp.children, privilegs)
      }
      res.push(tmp)
    }
  })
  return res
}

const permission = {
  state: {
    routers: constantRouterMap,    // 完整路由
    addRouters: [],                // 根据权限计算出的路由
    privilegs:[]                   // 权限数组
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    },
    SET_PRIVILEGS: (state, privilegs) => {
      state.privilegs = privilegs
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const privilegs = data.privilegs.split(',')
        // const privilegs = ['organ','role','user']
        let accessedRouters = filterRouter(asyncRouterMap, privilegs)
        commit('SET_ROUTERS', accessedRouters)
        commit('SET_PRIVILEGS', privilegs)
        resolve()
      })
    }
  }
}

export default permission
