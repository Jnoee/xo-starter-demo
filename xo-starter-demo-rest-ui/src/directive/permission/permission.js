
import store from '@/store'

/*  
    通过指令传入的值，去 vueX 中的 privilegs 进行匹配，匹配成功，返回true
*/

export default{
    inserted(el, binding, vnode) {
        const { value } = binding
        const privilegs = store.getters && store.getters.privilegs
        if (value && value instanceof Array && value.length > 0) {
            const permissionRoles = value
            
            const hasPermission = privilegs.some(privileg => {
                return permissionRoles.includes(privileg)
            })

            if (!hasPermission) {
                el.parentNode && el.parentNode.removeChild(el)
            }
        } else {
            throw new Error(`need privilegs! Like v-permission="['log','organ']"`)
        }
    }
}
