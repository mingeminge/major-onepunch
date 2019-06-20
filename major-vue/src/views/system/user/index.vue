<template>
  <div class="app-container">
    <el-button-group class="query-form-button">
      <el-button type="success" class="el-icon-search" @click="hide=!hide" v-if="hide">查询</el-button>
      <el-button type="success" class="el-icon-close" @click="hide=!hide" v-if="!hide">隐藏</el-button>
      <div v-has="['sysUser:add']">
        <el-button type="primary" @click="openAdd" v-has="['sysUser:add']" class="el-icon-plus">添加</el-button>
      </div>
    </el-button-group>
    <el-form :model="queryForm" inline class="query-form" v-show="!hide" ref="queryForm">
      <el-form-item label="英雄名" prop="username">
        <el-input v-model="queryForm.username"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="queryForm.phone"></el-input>
      </el-form-item>
      <el-form-item label="注册日期" prop="createTime">
        <el-date-picker
          v-model="queryForm.createTime"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
        <el-button type="primary" @click="resetForm('queryForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="tableData" ref="userTable" stripe style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="username" label="英雄名"></el-table-column>
      <el-table-column prop="nickName" label="英雄称号"></el-table-column>
      <el-table-column prop="sex" label="性别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex===1">男</el-tag>
          <el-tag type="danger" v-else-if="scope.row.sex===0">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="phone" label="手机号码"></el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱"></el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="createTime" label="创建日期"></el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="updateTime" label="修改日期"></el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="primary" @click="openEdit(scope.row.id)"
                       class="el-icon-edit"></el-button>
            <el-button type="danger" @click="deleteUser(scope.row.id)" v-has="['sysUser:delete']"
                       class="el-icon-delete"></el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-dialog :width="dialogWidth" :visible.sync="dialogVisible" title="编辑">
      <el-form v-loading="formLoading" ref="openForm" :model="openForm" label-width="80px">
        <el-form-item label="英雄名" prop="username" :rules="[{ required: true, message: '请输入英雄名', trigger: 'blur' }]">
          <el-input v-model="openForm.username"></el-input>
        </el-form-item>
        <el-form-item label="英雄称号" prop="nickName" :rules="[{ required: true, message: '请输入英雄称号', trigger: 'blur' }]">
          <el-input v-model="openForm.nickName"></el-input>
        </el-form-item>
        <el-form-item v-if="isAdd" label="密码" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' },
    { min:6,max:12,message:'密码长度5-12位',trigger: 'blur' }]">
          <el-input type="password" v-model="openForm.password"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },
    {min:11,max:11,message:'请输入合法的手机号码',trigger:'blur'}]">
          <el-input v-model="openForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}]">
          <el-input v-model="openForm.email"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :rules="[
      { required: true, message: '请选择性别', trigger: 'blur' }]">
          <el-radio-group v-model="openForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色权限" prop="roleIds">
          <el-checkbox-group v-model="openForm.roles">
            <el-checkbox v-for="item in roles" :label="item.id" :key="item.id">{{item.description}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit('openForm')">提交</el-button>
          <el-button @click="resetForm('openForm')">取消</el-button>
        </el-form-item>
      </el-form>
      <!--<div slot="footer" class="dialog-footer">

      </div>-->
    </el-dialog>
  </div>
</template>

<script>
  import {deleteById, findById, getRoleList, list, save, update} from "@/api/user";
  import {isMobile} from "@/utils";

  export default {
    name: "User",
    computed: {
      dialogWidth() {
        if (isMobile()) {
          return '90%'
        } else {
          return '50%'
        }
      },
      queryParam() {
        return {
          'page': this.queryForm.page,
          'limit': this.queryForm.limit,
          'username': this.queryForm.username,
          'phone': this.queryForm.phone,
          'startTime': this.queryForm.createTime[0],
          'endTime': this.queryForm.createTime[1]
        }
      }
    },
    data() {
      return {
        isAdd: false,
        loading: false,
        formLoading: false,
        tableData: [],
        total: 0,
        hide: true,
        queryForm: {
          page: 1, limit: 10, username: '', phone: '', createTime: ''
        },
        dialogVisible: false,
        openForm: {username: '', nickName: '', phone: '', email: '', sex: '', roles: []},
        roles: []
      }
    },
    mounted() {
      this.loadTable();
    },
    methods: {
      loadTable(params) {
        let _this = this;
        _this.loading = true;
        list(params).then(resp => {
          _this.tableData = resp.data;
          _this.loading = false;
          _this.total = resp.count;
        }).catch(() => {
          _this.loading = false;
        })
      },
      query() {
        this.loadTable(this.queryParam);
      },
      openEdit(id) {
        this.dialogVisible = true
        let _this = this;
        _this.getRoles();
        _this.formLoading = true;
        findById(id).then(resp => {
          _this.openForm = resp.data;
          let rolesArr = [];
          if (resp.data.roles && resp.data.roles.length > 0) {
            resp.data.roles.forEach(role => {
              rolesArr.push(role.id);
            });
          }
          _this.openForm.roles = rolesArr;
          _this.formLoading = false;
          _this.dialogVisible = true;
        }).catch(() => {
          _this.formLoading = false;
          _this.dialogVisible = false;
        })
      },
      getRoles() {
        let _this = this;
        _this.formLoading = true;
        getRoleList().then(resp => {
          if (resp.code === 200 && resp.data.length > 0) {
            _this.roles = resp.data;
            _this.formLoading = false;
          }
        }).catch(() => {
          _this.formLoading = false;
        })
      },
      submit(formName) {
        let _this = this;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            _this.openForm.updateTime = null;
            _this.openForm.createTime = null;
            _this.formLoading = true;
            let roles = _this.openForm.roles
            if (roles.length > 0) {
              let arr = [];
              roles.forEach(item => {
                arr.push({'id': item})
              });
              _this.openForm.roles = arr;
            }
            if (_this.isAdd) {
              _this.openForm.password = Base64.encode(_this.openForm.password);
              save(_this.openForm).then(resp => {
                if (resp.code === 200) {
                  _this.$message({
                    showClose: true,
                    message: '添加成功',
                    type: 'success'
                  });
                  _this.dialogVisible = false;
                  _this.formLoading = false;
                  _this.loadTable(_this.queryParam);
                } else {
                  return false;
                }
              }).catch(() => {
                _this.addFormVisible = false;
                _this.formLoading = false;
              })
            } else {
              update(_this.openForm).then(resp => {
                if (resp.code === 200) {
                  _this.formLoading = false;
                  _this.dialogVisible = false;
                  _this.$message({
                    type: 'success',
                    message: '修改成功!'
                  });
                  _this.loadTable(_this.queryParam);
                }
              }).catch(() => {
                _this.dialogVisible = false;
                _this.formLoading = false;
              })
            }
          } else {
            return false;
          }
        })
      },
      deleteUser(id) {
        let _this = this;
        _this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.formLoading = true;
          deleteById(id).then(resp => {
            if (resp.code === 200) {
              _this.$message({
                type: 'success',
                message: '删除成功!'
              });
              _this.loading = false;
              _this.loadTable(_this.queryParam);
            }
          }).catch(() => {
            _this.loading = false;
          })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
          _this.loading = false;
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.dialogVisible = false;
      },
      handleSizeChange(val) {
        this.queryForm.limit = val;
        this.loadTable(this.queryParam);
      },
      handleCurrentChange(val) {
        this.queryForm.page = val;
        this.loadTable(this.queryParam);
      },
      openAdd() {
        this.dialogVisible = true;
        this.isAdd = true;
        this.openForm = {roles: []}
        this.getRoles();
      }
    }
  }
</script>
<style scoped>
  @import "../../../styles/gloable.css";
</style>
