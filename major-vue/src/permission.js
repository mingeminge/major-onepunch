import router from './router'
import store from "./store";

const permission = router.beforeEach((to, from, next) => {
  let token = store.getters.token;
  let roles = store.getters.roles;
  if (!token) {
    if (to.path === '/login') {
      next();
    } else {
      next('/login')
    }
  } else if (roles.length === 0) {
    store.dispatch('UserInfo').then(() => {
      router.push(to.fullPath);
    }).catch(e => {
      next('/login')
    });
  } else {
    next();
  }
});
export default permission;
