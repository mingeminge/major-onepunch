import store from '../store'

export const initMenu = (router, menu) => {
  if (menu.length === 0) {
    return
  }
  let menus = formatRoutes(menu);
  // 最后添加
  let notFound = {path: '*', redirect: '/404', hidden: true}
  menus.push(notFound)
  router.addRoutes(menus)
  store.commit('ADD_ROUTERS', menus)
}


export const formatRoutes = (aMenu) => {
  const aRouter = []
  aMenu.forEach(oMenu => {
    const {
      path,
      component,
      name,
      icon,
      childrens
    } = oMenu
    if (!validatenull(component)) {
      let filePath;
      const oRouter = {
        path: path,
        component(resolve) {
          let componentPath = ''
          if (component === 'Layout') {
            require(['../views/layout/Layout'], resolve)
            return
          } else {
            componentPath = component
          }
          require([`../${componentPath}.vue`], resolve)
        },
        name: name,
        icon: icon,
        children: validatenull(childrens) ? [] : formatRoutes(childrens)
      }
      aRouter.push(oRouter)
    }

  })
  return aRouter
}

const validatenull = (component) => {
  if (component === '' || component === null) {
    return false;
  }
  return true;
}
