const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  avatar: state => state.app.avatar,
  token: state => state.app.token,
  username: state => state.app.username,
  roles: state => state.app.roles,
  authorities: state => state.app.authorities,
};

export default getters;
