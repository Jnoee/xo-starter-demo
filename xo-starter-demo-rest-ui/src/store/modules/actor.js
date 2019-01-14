import { getActorList } from '@/bin/api/person'
import { getCookies } from '@/bin/utils/auth'

const actor = {
    state: {
        actorList: []
    },

    mutations: {
        UPDATEACTORLIST(state, actorList) {
            state.actorList = actorList
        }
    },

    actions: {
        getActorLists({ commit }) {
            return new Promise((resolve, reject) => {
                // 查询职务列表
                const defaultInfo = JSON.parse(getCookies('userInfo'))
                getActorList(defaultInfo.id).then(res => {
                    commit('UPDATEACTORLIST', res.data)
                    resolve(res)
                }).catch(err => {
                    reject(error)
                })
            })
        }
    }
}

export default actor