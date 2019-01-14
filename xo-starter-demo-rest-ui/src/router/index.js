import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../views/layout/Layout'  // 引进layout组件

Vue.use(Router)

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/

// 静态路由
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  {
    path: '/',
    component: Layout,
    name: 'system',
    redirect: '/system',
    // alwaysShow: true,
    // hidden: true,
    meta: { title: '系统管理' },
    children: [{
      path: 'system',
      name: 'Home',
      component: () => import('@/views/system/index'),
      meta: { title: '首页', icon: 'example' }
    }]
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }]
  },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})


// 需要根据权限动态添加的路由
export const asyncRouterMap = [
  {
    path: '/authority',
    component: Layout,
    redirect: 'noredirect',
    name: 'Authoritys',
    meta: { title: '权限管理', icon: 'tree' },
    children: [
      {
        path: 'organ',
        name: 'Organ',
        component: () => import('@/views/authority/organ/index'),
        meta: { title: '机构管理', icon: 'nested', privilegs: ['organ'] },
      },
      {
        path: 'role',
        name: 'Roles',
        component: () => import('@/views/authority/role/index'),
        meta: { title: '角色管理', icon: 'tree', privilegs: ['role'] },
      },
      {
        path: 'user',
        name: 'Users',
        component: () => import('@/views/authority/user/index'),
        meta: { title: '用户管理', icon: 'user', privilegs: ['user'] },
      }
    ]
  },
  {
    path: '/maintain',
    component: Layout,
    redirect: 'noredirect',
    name: 'Maintains',
    meta: {
      title: '系统维护',
      icon: 'tree'
    },
    children: [
      {
        path: 'log',
        component: () => import('@/views/maintain/log/index'), // Parent router-view
        name: 'Logs',
        meta: { title: '日志管理', icon: 'bug', privilegs: ['log'] },
      },
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/maintain/indexes/index'),
        meta: { title: '全文索引', icon: 'list', privilegs: ['index'] },
      },
      {
        path: 'cache',
        name: 'Caches',
        component: () => import('@/views/maintain/cache/index'),
        meta: { title: '实体缓存', icon: 'nested', privilegs: ['cache'] },
      }
    ]
  },
  { path: '/401', component: () => import('@/views/401'), hidden: true },
  { path: '*', redirect: '/404', hidden: true }
]
