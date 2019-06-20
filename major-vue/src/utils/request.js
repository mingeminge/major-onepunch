import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'


const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 30000,
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['token'] = store.getters.token;
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.code !== 200) {
      if (res.code === 201) {
        MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('Logout').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      else if (res.code === 204) {
        return res;
        //store.dispatch('SetLoginErrMsg', res.message);
      }
      else if (res.code === 205) {
        /*MessageBox.confirm(
          '身份信息已过期，请重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('Logout').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })*/
        store.dispatch('Logout').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      } else {
        MessageBox.confirm(res.message, '异常', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
      }
      return Promise.reject('error')
    } else {
      return res;
    }
  },
  error => {
    Message({
      message: '无法连接到本地代理程序，请确认代理程序是否运行正常！',
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error)
  }
);

export default service
