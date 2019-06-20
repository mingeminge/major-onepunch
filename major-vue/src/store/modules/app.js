import {login, logout, userInfo} from "@/api/user";

const app = {
  state: {
    sidebar: {
      opened: !+localStorage.getItem('sidebarStatus'),
      withoutAnimation: false
    },
    open: false,
    device: 'desktop',
    username: '',
    roles: '',
    authorities: '',
    token: localStorage.getItem('token'),
    avatar: '',
    menus: ''
  },
  mutations: {
    SET_AUTHORITIES: (state, authorities) => {
      state.authorities = authorities;
    },
    SET_USERNAME: (state, username) => {
      state.username = username;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_TOKEN: (state, token) => {
      localStorage.setItem('token', token)
      state.token = token
    },
    SET_HEAD_IMG: (state, avatar) => {
      state.avatar = avatar;
    },
    TOGGLE_SIDEBAR: state => {
      if (state.sidebar.opened) {
        localStorage.setItem('sidebarStatus', 1)
      } else {
        localStorage.setItem('sidebarStatus', 0)
      }
      state.sidebar.opened = !state.sidebar.opened
      state.sidebar.withoutAnimation = false
    },
    CLOSE_SIDEBAR: (state, withoutAnimation) => {
      localStorage.setItem('sidebarStatus', 1)
      state.sidebar.opened = false
      state.sidebar.withoutAnimation = withoutAnimation
    },
    TOGGLE_DEVICE: (state, device) => {
      state.device = device
    }
  },
  actions: {
    SetHeadImg: ({commit}, url) => {
      commit('SET_HEAD_IMG', url);
    },
    Login: ({commit}, param) => {
      return new Promise((resolve, reject) => {
        login(param).then(resp => {
          if (resp.code === 200) {
            commit('SET_USERNAME', resp.data.username);
            commit('SET_ROLES', resp.data.roles);
            commit('SET_TOKEN', resp.data.token);
            commit('SET_HEAD_IMG', resp.data.imgUrl);
            commit('SET_AUTHORITIES', resp.data.authorities);
          }
          resolve(resp);
        }).catch(error => {
          reject(error)
        })
      });
    },
    Logout: ({commit}) => {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_USERNAME', '');
          commit('SET_ROLES', '');
          commit('SET_TOKEN', '');
          commit('SET_HEAD_IMG', '');
          commit('SET_AUTHORITIES', []);
          resolve();
        }).catch(error => {
          reject(error)
        })
      });
    },
    UserInfo: ({commit}) => {
      return new Promise((resolve, reject) => {
        userInfo().then(resp => {
          if (resp.code === 200) {
            commit('SET_USERNAME', resp.data.username);
            commit('SET_ROLES', resp.data.roles);
            commit('SET_TOKEN', resp.data.token);
            commit('SET_HEAD_IMG', resp.data.imgUrl);
            commit('SET_AUTHORITIES', resp.data.authorities);
            resolve();
          } else {
            reject('身份信息已过期!');
          }
        }).catch(error => {
          reject(error);
        })
      })
    },
    ToggleSideBar: ({commit}) => {
      commit('TOGGLE_SIDEBAR')
    },
    CloseSideBar({commit}, {withoutAnimation}) {
      commit('CLOSE_SIDEBAR', withoutAnimation)
    },
    ToggleDevice({commit}, device) {
      commit('TOGGLE_DEVICE', device)
    }
  },
};

export default app;
