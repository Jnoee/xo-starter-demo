import router from './router'
import NProgress from 'nprogress' 
import 'nprogress/nprogress.css'
import store from '@/store'
import { getToken } from '@/bin/utils/auth'
import { getCookies } from './bin/utils/auth';

// 判断是否有权限进入页面
function hasPermission(permissions, privileg) {
  if (!privileg) return true
  return permissions.some(permission => privileg.indexOf(permission) >= 0)
}

const whiteList = ['/login'] // 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({
        path: '/'
      })
      NProgress.done()
    } else {
      // 根据权限生成可访问的路由表
      if(store.getters.privilegs.length === 0) {
        const privilegs = getCookies('privilegs')
        store.dispatch('GenerateRoutes', { privilegs }).then(() => {
          router.onReady(()=>{
            router.addRoutes(store.getters.addRouters)
          })
          next()
        })
      } else {
        // 判断是否有权限进入页面
        if (hasPermission(store.getters.privilegs, to.meta.privilegs)) {
          next()
        }else{
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
      }
      
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
