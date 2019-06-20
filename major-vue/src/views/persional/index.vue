<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="个人信息" name="info" v-loading="loading">
        <el-card shadow="always">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="editVisible=true">编辑资料</el-button>
          </div>
          <el-row :gutter="20">
            <el-col :span="6" :xs="24" :md="6">
              <div style="text-align: center">
                <div>
                  <el-image style="width: 150px;height: 150px" fit="scale-down"
                            :src="IMG_URL+avatar"></el-image>
                </div>
                <div>
                  <el-button @click="openImgDialog">修改头像</el-button>
                </div>
              </div>
            </el-col>
            <el-col :span="12" :xs="24" :md="6">
              <el-form label-width="80px" label-position="left" :model="userInfo" status-icon>
                <el-form-item label="用户名">
                  {{userInfo.username}}
                </el-form-item>
                <el-form-item label="昵称">
                  {{userInfo.nickName}}
                </el-form-item>
                <el-form-item label="注册时间">
                  {{userInfo.createTime}}
                </el-form-item>
                <el-form-item label="邮箱地址">
                  {{userInfo.email}}
                </el-form-item>
                <el-form-item label="手机号码">
                  {{userInfo.phone}}
                </el-form-item>
                <el-form-item label="性别">
                  <div v-if="userInfo.sex===1">男</div>
                  <div v-else-if="userInfo.sex===0">女</div>
                  <div v-else>其他</div>
                </el-form-item>
                <el-form-item label="个性签名">
                  <el-input type="textarea" autosize v-model="userInfo.signature"></el-input>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="修改密码" name="password" v-loading="loading">
        <el-card>
          <div slot="header" class="clearfix">
            <span>修改密码</span>
          </div>
          <el-form label-width="80px" label-position="left" ref="passwordForm" status-icon :model="form" :rules="rules">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input type="password" v-model="form.oldPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input type="password" v-model="form.newPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkNewPassword">
              <el-input type="password" v-model="form.checkNewPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="updatePassword">修改</el-button>
              <el-button @click="resetFields('passwordForm')">取消</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
    </el-tabs>
    <el-dialog center :width="dialogWidth" :visible.sync="editVisible" title="修改">
      <el-form v-loading="formLoading" label-width="80px" label-position="left" :model="userInfo" status-icon>
        <el-form-item label="用户名">
          {{userInfo.username}}
        </el-form-item>
        <el-form-item label="昵称" prop="nickName" :rules="[{ required: true, message: '请输入昵称', trigger: 'blur' }]">
          <el-input v-model="userInfo.nickName"></el-input>
        </el-form-item>
        <el-form-item label="注册时间">
          {{userInfo.createTime}}
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}]">
          <el-input v-model="userInfo.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },
    {min:11,max:11,message:'请输入合法的手机号码',trigger:'blur'}]">
          <el-input v-model="userInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="userInfo.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="个性签名">
          <el-input type="textarea" autosize v-model="userInfo.signature"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateInfo">修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog center :width="dialogWidth" :visible.sync="imgVisible" title="选择头像">
      <el-tabs v-loading="imgLoading" v-model="imgActiveName" @tab-click="tabsClick">
        <el-tab-pane label="英雄" name="1">
          <el-row :gutter="20">
            <el-col v-for="item in imgList" :key="item.id" :span="4" :xs="8" class="head-image">
              <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <el-image fit="scale-down" @click="updateHeadImg(item)" :key="item.id"
                          style="width: 100px; height: 100px"
                          :src="IMG_URL+item.imgUrl" lazy></el-image>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="怪人" name="2">
          <el-row :gutter="20">
            <el-col v-for="item in imgList" :key="item.id" :span="4" :xs="8" class="head-image">
              <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <el-image fit="scale-down" @click="updateHeadImg(item)" :key="item.id"
                          style="width: 100px; height: 100px"
                          :src="IMG_URL+item.imgUrl" lazy></el-image>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="其他" name="0">
          <el-row :gutter="20">
            <el-col v-for="item in imgList" :key="item.id" :span="4" :xs="8" class="head-image">
              <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <el-image fit="scale-down" @click="updateHeadImg(item)" :key="item.id"
                          style="width: 100px; height: 100px"
                          :src="IMG_URL+item.imgUrl" lazy></el-image>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
  import {findByUsername, updateInfo, updatePass, updateImg} from "@/api/personal";
  import {findAll} from "@/api/image";
  import {mapGetters} from 'vuex'
  import {isMobile} from "@/utils";

  export default {
    name: "PersonalCenter",
    computed: {
      ...mapGetters([
        'avatar',
        'username'
      ]),
      dialogWidth() {
        if (isMobile()) {
          return '90%'
        } else {
          return '50%'
        }
      }
    },
    data() {
      let validateNewPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        IMG_URL: process.env.IMG_URL,
        loading: false,
        activeName: 'info',
        imgLoading: false,
        formLoading: false,
        imgList: [],
        userInfo: {
          id: '',
          username: '',
          email: '',
          phone: '',
          sex: null,
          createTime: '',
          nickName: '',
          signature:''
        },
        form: {
          oldPassword: '',
          newPassword: '',
          checkNewPassword: ''
        },
        rules: {
          oldPassword: [{required: true, message: '请输入旧密码', trigger: 'blur'}
            , {min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur'}],
          newPassword: [
            {required: true, message: '请输入旧密码', trigger: 'blur'}
            , {min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur'}
          ],
          checkNewPassword: [
            {validator: validateNewPassword, trigger: 'blur'}
          ],
        },
        editVisible: false,
        imgVisible: false,
        imgActiveName: '1'
      }
    },
    mounted() {
      this.getInfo();
    },
    methods: {
      getInfo() {
        let _this = this;
        _this.loading = true;
        let username = _this.$store.getters.username;
        findByUsername(username).then(resp => {
          _this.userInfo = resp.data;
          _this.loading = false;
          _this.$store.dispatch('SetHeadImg', resp.data.imgUrl)
        }).catch(() => {
          _this.loading = false;
        })
      },
      getHeadImgs(params) {
        let _this = this;
        _this.imgLoading = true;
        findAll(params).then(resp => {
          if (resp.code === 200) {
            _this.imgList = resp.data;
            _this.imgLoading = false;
          }
        }).catch(() => {
          _this.imgLoading = false;
        })
      },
      updateInfo() {
        let _this = this;
        _this.userInfo.createTime=null;
        _this.formLoading = true;
        updateInfo(_this.userInfo).then(resp => {
          if (resp.code === 200) {
            _this.$message({
              type: 'success',
              message: '修改成功!'
            });
            _this.editVisible = false;
            _this.formLoading = false;
          }
        }).catch(e => {
          _this.$message.error(e);
          _this.formLoading = false;
          _this.editVisible = false;
        })
      },
      updatePassword() {
        let _this = this;
        this.$refs['passwordForm'].validate((valid) => {
          let params = {
            'username': _this.userInfo.username,
            'oldPassword': Base64.encode(_this.form.oldPassword),
            'newPassword': Base64.encode(_this.form.newPassword)
          };
          if (valid) {
            _this.loading = true;
            updatePass(params).then(resp => {
              _this.$message({
                type: 'success',
                message: '修改成功!'
              });
              _this.loading = false;
              this.$refs['passwordForm'].resetFields();
            }).catch(e => {
              _this.$message.error(e);
              _this.loading = false;
            })
          } else {
            return false;
          }
        });
      },
      resetFields(formName) {
        this.$refs[formName].resetFields();
      },
      tabsClick(tab, event) {
        this.getHeadImgs({'type': tab.name});
      },
      updateHeadImg(url) {
        console.log(url)
        let _this = this;
        let imgUrl = url.imgUrl;
        let params = {'imgUrl': imgUrl, 'username': _this.username};
        _this.loading = true;
        updateImg(params).then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.imgVisible = false;
            _this.$store.dispatch('SetAvatar', imgUrl)
            _this.$message.success('更换头像成功')
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      openImgDialog() {
        this.imgVisible = true;
        this.getHeadImgs({'type': this.imgActiveName});
      }
    }
  }
</script>

<style scoped>
  .head-image {
    margin-bottom: 10px;
  }
</style>
