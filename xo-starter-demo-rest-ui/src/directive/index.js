import permission from './permission/permission'

let directive = {}
const install = function(Vue) {
  Vue.directive('permission', permission);
}

directive.install = install
export default directive
