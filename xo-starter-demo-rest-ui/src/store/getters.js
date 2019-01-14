const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  name: state => state.user.name,
  privilegs: state => state.permission.privilegs,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
  actorList: state => state.actor.actorList
}
export default getters

