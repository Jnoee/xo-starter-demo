import { login, logout, getInfo } from '@/bin/api/login'
import { getToken, setToken, removeToken, setCookies, removeCookies } from '@/bin/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    privilegs: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.headers
          const privileg = response.data.privilegs
          const userInfo = {
            id: response.data.id,
            name: response.data.name,
            organName: response.data.organName,
            roleName: response.data.roleName,
          }
          let privilegs = ''
          privileg.map((item) => {
            item = item.split(':')[0]
            if(privilegs === '') {
              privilegs = item
            } else {
              privilegs =privilegs + ',' + item
            }
          })
          setToken(data['x-auth-token'])
          setCookies('userInfo', JSON.stringify(userInfo))
          setCookies('privilegs', privilegs)
          commit('SET_TOKEN', data['x-auth-token'])
          resolve(response.data)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          removeCookies('userInfo')
          removeCookies('privilegs')
          removeCookies('sidebarStatus')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        removeCookies('userInfo')
        removeCookies('privilegs')        
        removeCookies('sidebarStatus')        
        resolve()
      })
    }
  }
}

export default user
