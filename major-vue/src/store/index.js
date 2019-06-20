import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import getters from './getter'
import tagsView from './modules/tagsView'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    tagsView
  },
  getters
});

export default store
