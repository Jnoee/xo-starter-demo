<template>
  <div id="Navbar">
    <el-menu class="navbar" mode="horizontal">
      <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
      <breadcrumb />
      <div class="role">
        {{defaultInfo.name}} &nbsp;({{defaultInfo.organName}}--{{defaultInfo.roleName}})
        <el-dropdown class="role-name" trigger="click" @command="toggleActors">
          <span class="toggle-role">[切换职务]</span>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <el-dropdown-item
              :style="item.defaulted === true ? 'display:block;background-color: #ecf5ff;color: #66b1ff' : 'display:block;'"
              v-for="(item, index) in actorList"
              :key="index"
              :command="item"
              :disabled="item.defaulted"
            >
              <span>{{item.name}} ({{item.organName}}--{{item.roleName}})</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-dropdown class="avatar-container" size="medium" trigger="click">
        <div class="avatar-wrapper">
          <img :src="headerImg" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link class="inlineBlock" to="/">
            <el-dropdown-item>
              回到首页
            </el-dropdown-item>
          </router-link>
          <div @click="dialogShow">
            <el-dropdown-item divided>
              <span style="display:block;">修改密码</span>
            </el-dropdown-item>
          </div>
          <div @click="logout">
            <el-dropdown-item divided>
              <span style="display:block;">退出登录</span>
            </el-dropdown-item>
          </div>
        </el-dropdown-menu>
      </el-dropdown>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { setCookies, getCookies } from '@/bin/utils/auth'
import { getActorList, toggleActor, changePwd } from '@/bin/api/person'
import { getRoleInfo } from '@/bin/api/roles'

import headerImg from '@/assets/tx.jpg'
import Hamburger from '@/components/Hamburger'
import Breadcrumb from '@/components/Breadcrumb'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      headerImg,
      defaultInfo: {}
    }
  },
  mounted() {
    // 获取用户信息
    this.defaultInfo = JSON.parse(getCookies('userInfo'))

    // 获取职务列表
    this.initData()
  },
  computed: {
    ...mapGetters(['sidebar','actorList'])
  },
  methods: {

    // 切换侧边栏
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },

    // 初始化数据，获取职务列表
    initData() {
      // 查询职务列表
      if(this.actorList.length === 0) {
        this.$store.dispatch('getActorLists').then(res => {
        })
      }
    },

    // 切换职务
    toggleActors(data){
      if(data.id === this.defaultInfo.id) return false
      toggleActor(data.id).then(res => {
        // 重新加载角色信息
        let userInfo = {
          id: data.id,
          name: data.name,
          organName: data.organName,
          roleName: data.roleName,
        }
        setCookies('userInfo', JSON.stringify(userInfo))

        // 重新获取权限
        let privilegs = ''
        res.data.map((item) => {
          item = item.split(':')[0]
          if(privilegs === '') {
            privilegs = item
          } else {
            privilegs = privilegs + ',' + item
          }
        })
        setCookies('privilegs', privilegs)
        // 先将路由重定向至首页， 然后延时刷新浏览器，避免还没跳转至首页，浏览器已刷新，出现 404 找不到页面的问题
        this.$router.push('/')
        setTimeout(() => {
          location.reload()
        },100)
      }).catch(err => {
        return false
      })
    },

    // 退出登录
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      }).catch((error) => {
        console.log(error)
      })
    },

    // 向父级传递显示 修改密码 对话框信息
    dialogShow() {
      this.$emit('resetPwdShow')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  border-radius: 0px !important;
  p {
    margin: 0;
    float: left;
  }
  .pwd-tips {
    width: 25%;
  }
  .pwd-key {
    width: 70%;
  }
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .role {
    position: absolute;
    right: 100px;
    top: 16px;
    margin: 0;
    font-size: 14px;
    font-weight: 700;
    outline: none;
    .toggle-role {
      font-weight: 100;
      color: #42a4ff;
      cursor: pointer;
      &:hover{
        color: #0684fa;
      }
    }
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    top: 0;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

