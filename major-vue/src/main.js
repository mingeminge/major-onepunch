// The Vue build version to loading with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'normalize.css/normalize.css'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import permission from './permission'
import '@/icons'
import '@/styles/index.scss' // global css
import '@/styles/gloable.css'
import echarts from 'echarts';
import VueParticles from 'vue-particles'
import has from './directive/permission/index'

Vue.config.productionTip = false
let Base64 = require('js-base64').Base64;

import {
  Form,
  FormItem,
  Row,
  Col,
  Input,
  Button,
  ButtonGroup,
  Menu,
  MenuItem,
  MenuItemGroup,
  RadioGroup,
  Radio,
  RadioButton,
  Submenu,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  Breadcrumb,
  BreadcrumbItem,
  Scrollbar,
  Table,
  TableColumn,
  Loading,
  Tag,
  DatePicker,
  Dialog,
  Card,
  Message,
  Checkbox,
  CheckboxGroup,
  MessageBox,
  Notification,
  Select,
  Option,
  Pagination,
  Progress,
  TabPane,
  Tabs,
  Image,
  Tree,
  Upload
} from 'element-ui';

Vue.use(Form);
Vue.use(FormItem)
Vue.use(Row)
Vue.use(Col)
Vue.use(Input)
Vue.use(Button)
Vue.use(ButtonGroup)
Vue.use(MenuItemGroup)
Vue.use(MenuItem)
Vue.use(Menu)
Vue.use(RadioButton)
Vue.use(RadioGroup)
Vue.use(Radio)
Vue.use(Submenu)
Vue.use(DropdownItem)
Vue.use(Dropdown)
Vue.use(DropdownMenu)
Vue.use(BreadcrumbItem)
Vue.use(Breadcrumb)
Vue.use(Scrollbar)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Loading)
Vue.use(Tag)
Vue.use(DatePicker)
Vue.use(Dialog)
Vue.use(Card)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Select)
Vue.use(Option)
Vue.use(Pagination)
Vue.use(Progress)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Image)
Vue.use(Tree)
Vue.use(Upload)
Vue.prototype.$echarts = echarts;
Vue.prototype.$loading = Loading.service;
Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$alert = MessageBox.alert;
Vue.prototype.$confirm = MessageBox.confirm;
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$notify = Notification;
Vue.prototype.$message = Message;
Vue.prototype.$ELEMENT = {size: 'small', zIndex: 3000};
Vue.use(VueParticles)
new Vue({
  el: '#app',
  router,
  store,
  permission,
  components: {App},
  template: '<App></App>'
})
