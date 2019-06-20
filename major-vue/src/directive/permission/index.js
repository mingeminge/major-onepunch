import Vue from 'vue'
import store from '../../store'

const has = Vue.directive('has', {
  bind: function (el, binding, vnode) {
    // 获取按钮权限
    if (!Vue.prototype.$_has(binding.value)) {
      setTimeout(() => el.parentNode.removeChild(el))
    }

  }
});
// 权限检查方法
Vue.prototype.$_has = function (value) {
  let authority = store.getters.authorities;
  let authorities;
  if (authority.indexOf(',') > -1) {
    authorities = authority.split(',');
  } else {
    authorities = authority;
  }
  if (authorities === undefined || authorities == null || authorities === '') {
    return false;
  }
  if (value && value instanceof Array && value.length > 0) {
    const permissionRoles = value;

    return authorities.some(role => {
      return permissionRoles.includes(role)
    });
  } else {
    throw new Error(`need roles! Like v-permission="['admin','editor']"`)
  }
};
export {has};
