import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      hidden: true,
      component: resolve => require(['../views/login/index'], resolve)
    },
    {
      path: '/',
      component: resolve => require(['../views/layout/Layout'], resolve),
      redirect: 'index',
      children: [{
        path: 'index',
        name: 'index',
        component: resolve => require(['../views/index/index'], resolve),
        meta: {title: '首页', icon: 'home'}
      }]
    },
    {
      path: '/user',
      component: resolve => require(['../views/layout/Layout'], resolve),
      redirect: '/user/info',
      hidden: true,
      children: [{
        path: 'info',
        name: 'info',
        component: resolve => require(['../views/persional/index'], resolve),
        meta: {title: '个人中心', icon: 'example'}
      }]
    },
    {
      path: '/system',
      meta: {title: '系统管理', icon: 'setting', roles: ['ROLE_ADMIN']},
      redirect: '/system/user',
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [{
        path: 'user',
        name: 'user',
        component: resolve => require(['../views/system/user/index'], resolve),
        meta: {title: '用户管理', icon: 'user'}
      },
        {
          path: 'role',
          name: 'role',
          component: resolve => require(['../views/system/role/index'], resolve),
          meta: {title: '角色管理', icon: 'role'}
        },
        {
          path: 'authority',
          name: 'authority',
          component: resolve => require(['../views/system/authority/index'], resolve),
          meta: {title: '权限管理', icon: 'role'}
        }]
    },
    {
      path: '/syslog',
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [{
        path: 'syslog',
        name: 'log',
        component: resolve => require(['../views/log/index'], resolve),
        meta: {title: '系统日志', icon: 'log', roles: ['ROLE_ADMIN']}
      }]
    },
    {
      path: '/active',
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [{
        path: 'activeUser',
        name: 'activeUser',
        component: resolve => require(['../views/active/index'], resolve),
        meta: {title: '在线用户', icon: 'activeUser'}
      }]
    },

    {
      path: '/image',
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [{
        path: 'image',
        name: 'image',
        component: resolve => require(['../views/image/index'], resolve),
        meta: {title: '头像管理', icon: 'image'}
      }]
    },
    /*{
      path: '/cartoon',
      meta: {title: '一拳漫画', icon: 'setting'},
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [{
        path: 'one',
        name: 'one',
        component: resolve => require(['../views/cartoon/one/index'], resolve),
        meta: {title: 'ONE原作', icon: 'image'}
      }, {
        path: 'murata',
        name: 'murata',
        component: resolve => require(['../views/cartoon/murata/index'], resolve),
        meta: {title: '村田重制版', icon: 'image'}
      }]
    },*/
    {
      path: '/GitHub',
      component: resolve => require(['../views/layout/Layout'], resolve),
      children: [
        {
          path: 'https://github.com/mingeminge/major-onepunch',
          meta: { title: 'GitHub', icon: "github" }
        }
      ]
    },
    {path: '/*', component: () => import('@/views/404/404'), hidden: false}
  ]
});

export const asyncRouter = [
  {
    path: '/system',
    meta: {title: '系统管理', icon: 'setting', roles: ['ROLE_ADMIN']},
    redirect: '/system/user',
    component: resolve => require(['../views/layout/Layout'], resolve),
    children: [{
      path: 'user',
      name: 'user',
      component: resolve => require(['../views/system/user/index'], resolve),
      meta: {title: '用户管理', icon: 'user'}
    },
      {
        path: 'role',
        name: 'role',
        component: resolve => require(['../views/system/role/index'], resolve),
        meta: {title: '角色管理', icon: 'role'}
      },
      {
        path: 'authority',
        name: 'authority',
        component: resolve => require(['../views/system/authority/index'], resolve),
        meta: {title: '权限管理', icon: 'role'}
      },
      {
        path: 'syslog',
        name: 'log',
        component: resolve => require(['../views/log/index'], resolve),
        meta: {title: '系统日志', icon: 'log', roles: ['ROLE_ADMIN']}
      },
      {
        path: 'activeUser',
        name: 'activeUser',
        component: resolve => require(['../views/active/index'], resolve),
        meta: {title: '在线用户', icon: 'activeUser'}
      }, {
        path: 'image',
        name: 'image',
        component: resolve => require(['../views/image/index'], resolve),
        meta: {title: '头像管理', icon: 'image'}
      }]
  }
  /**/];
