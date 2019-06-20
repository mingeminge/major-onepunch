<template>
  <div class="bg" v-loading="gifLoading"
       element-loading-text="正在加载超级炫酷的动画! ๑乛◡乛๑ ">
    <el-image @load="loadImgSuccess" v-show="showGif" style="height: 100%;width: 100%;position: absolute;z-index: 3333"
              :src="loginGif"></el-image>
    <el-image :fit="fit" :src="bgImg"></el-image>
    <div class="login-form animated flipInY" :title="'兴趣使然的Hero'">
      <h3>一击男</h3>
      <h3></h3>
      <el-form ref="loginForm" status-icon :model="form" :rules="rules">
        <el-form-item prop="username" :error="errorMsg">
          <el-input placeholder="账号" prefix-icon="el-icon-user"
                    v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" :error="errorMsg">
          <el-input placeholder="密码" prefix-icon="el-icon-lock" v-model="form.password" show-password
                    type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="star"
      :particleSize="3"
      linesColor="#fff"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="repulse">
    </vue-particles>
  </div>
</template>

<script>
  import {exitFullScreen, fullScreen, isMobile} from "@/utils";

  export default {
    name: "index",
    data() {
      return {
        form: {
          username: '',
          password: '',
        },
        loading: false,
        rules: {
          username: [{required: true, message: '请输入用户名', trigger: 'change'},
            {min: 4, message: '用户名至少4位', trigger: 'change'}],
          password: [{required: true, message: '请输入密码', trigger: 'change'},
            {min: 6, max: 12, message: '密码长度在6-12位之间', trigger: 'change'}]
        },
        gifLoading: true,
        showGif: false,
        errorMsg: ''
      }
    },
    mounted() {
      let _this = this;
      document.onkeydown = function (e) {
        let key = window.event.keyCode;
        if (key === 13) {
          _this.login();
        }
      }
    },
    computed: {
      redirect() {
        return this.$router.params.redirect;
      },
      loginGif() {
        return '../../../static/images/gif/login' + Math.floor(Math.random() * 10 + 1) + '.gif'
      },
      fit() {
        if (isMobile()) {
          return 'scale-down;';
        } else {
          return 'cover';
        }
      },
      bgImg() {
        if (isMobile()) {
          return '../../../static/images/loginBackground/bg2.jpeg';
        } else {
          return '../../../static/images/loginBackground/bg.jpeg'
        }
      }
    },
    methods: {
      loadImgSuccess(e) {
        this.gifLoading = false;
      },
      login() {
        let _this = this;
        _this.loading = true;
        _this.$refs['loginForm'].validate(valid => {
          if (valid) {
            _this.loading = true;
            let param = {'username': _this.form.username, 'password': Base64.encode(_this.form.password)}
            _this.$store.dispatch('Login', param).then(resp => {
              if (resp.code === 200) {
                if (!isMobile()) {
                  // fullScreen();
                }
                _this.showGif = true;
                setTimeout(function () {
                  _this.showGif = false;
                  if (!isMobile()) {
                    //exitFullScreen();
                  }
                  setTimeout(function () {
                    _this.loading = false;
                    _this.$router.push('/index')
                  }, 100)
                }, 5000)
              } else {
                _this.errorMsg = resp.message;
                _this.loading = false;
              }
            }).catch(() => {
              _this.loading = false;
            });
          } else {
            _this.loading = false;
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped lang="less">
  .bg {
    position: relative;
    height: 100%;
    width: 100%;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    #particles-js {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
    }
  }

  .login-form {
    position: absolute;
    border-radius: 5px;
    padding: 20px;
    z-index: 3;
    /* margin-left: 60%;
    margin-top: 10%;*/
    background: rgba(216, 220, 229, 0.4);
    .el-form-item {
      margin-bottom: 25px !important;
    }
    h3 {
      text-align: center;
      color: #ebedef;
      margin-top: 0px;
      margin-bottom: 5px;
      span {
        color: #20a0ff;
      }
    }
    form {
      margin-top: 25px;
      .el-form-item {
        margin-bottom: 15px;
      }
    }
    a {
      text-decoration: none;
      color: #1f2d3d;
    }
    button {
      width: 100%;
      font-weight: 600;
    }
  }
</style>
