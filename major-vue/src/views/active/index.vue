<template>
  <div class="app-container" v-loading="loading">
    <el-table :data="tableData" ref="aliveUserTable" stripe style="width: 100%">
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="country" label="登录国家"></el-table-column>
      <el-table-column prop="region" label="登录省份"></el-table-column>
      <el-table-column prop="city" label="登录城市"></el-table-column>
      <el-table-column prop="isp" label="网络类型"></el-table-column>
      <el-table-column prop="loginTime" label="登录时间"></el-table-column>
      <el-table-column prop="ip" label="IP地址"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-has="['sysOnlineUser:delete']" type="danger" @click="killUser(scope.row.token)">强制下线</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {getOnlineUserList, killOnlineUser} from "@/api/aliveUser";

  export default {
    name: "AliveUser",
    data() {
      return {
        loading: false,
        tableData: []
      }
    },
    mounted() {
      this.getAliveUsers();
    },
    methods: {
      getAliveUsers() {
        let _this = this;
        _this.loading = true;
        getOnlineUserList().then(resp => {
          if (resp.code === 200) {
            _this.tableData = resp.data;
            _this.loading = false;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      killUser(token) {
        let _this = this;
        _this.$confirm('强制下线后用户将不可继续操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
          if (token === _this.$store.getters.token) {
            _this.$confirm('自己踢自己?是个狼人!', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              killOnlineUser(token).then(resp => {
                if (resp.code === 200) {
                  _this.$store.dispatch('Logout').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                    _this.$router.push('/login')
                  })
                }
              }).catch(() => {
                _this.$message.success("强制下线失败");
                _this.loading = false;
              })
            });
          } else {
            killOnlineUser(token).then(resp => {
              if (resp.code === 200) {
                _this.$message.success("强制下线成功");
                _this.loading = false;
                this.getAliveUsers();
              }
            }).catch(() => {
              _this.$message.success("强制下线失败");
              _this.loading = false;
            })
          }
        }).catch(() => {
          _this.loading = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
