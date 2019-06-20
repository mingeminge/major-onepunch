<template>
  <div class="app-container">
    <el-button-group class="query-form-button">
      <el-button type="primary" class="el-icon-circle-plus-outline" v-has="['sysRole:add']" @click="openEdit(null)">添加
      </el-button>
    </el-button-group>
    <el-table :data="tableData" v-loading="loading" ref="roleTable" stripe style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="roleName" label="角色名"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="available" label="是否可用">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.available===1">可用</el-tag>
          <el-tag type="danger" v-else>不可用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="primary" v-has="['sysRole:update']" @click="openEdit(scope.row.id)"
                       class="el-icon-edit"></el-button>
            <el-button type="danger" v-has="['sysRole:delete']" @click="deleteRoleById(scope.row.id)"
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
    <el-dialog :width="dialogWidth" :visible.sync="dialogVisible" :title="isAdd?'添加新角色':'编辑角色'">
      <el-form ref="openForm" v-loading="formLoading" :model="openForm" label-width="80px" label-position="left">
        <el-form-item label="角色名" prop="roleName" :rules="[{ required: true, message: '请输入角色名', trigger: 'blur' }]">
          <el-input v-model="openForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="openForm.description"></el-input>
        </el-form-item>
        <el-form-item label="是否可用" prop="available">
          <el-select v-model="openForm.available">
            <el-option :value="1" label="是"></el-option>
            <el-option :value="0" label="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限">
          <el-tree
            :data="authorityData"
            show-checkbox
            ref="authorityTree"
            node-key="id"
            v-loading="treeLoading"
            :props="defaultProps">
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitEdit('openForm')" type="primary" class="el-icon-success">提交</el-button>
        <el-button @click="resetFields('openForm')" class="el-icon-error">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {deleteRole, findAll, findAuthorityById, findAuthorityList, findById, save, update} from "@/api/role";
  import {isMobile} from "@/utils";

  export default {
    name: "Role",
    data() {
      return {
        isAdd: true,
        tableData: [],
        loading: false,
        addFormVisible: false,
        dialogVisible: false,
        total: 0,
        addForm: {},
        openForm: {},
        authorityData: [],
        defaultProps: {
          children: 'children',
          label: 'authorityName'
        },
        treeLoading: false,
        formLoading: false
      }
    },
    computed: {
      dialogWidth() {
        if (isMobile()) {
          return '90%'
        } else {
          return '50%'
        }
      }
    },
    mounted() {
      this.loadTable();
    },
    methods: {
      getAuthorityList() {
        let _this = this;
        _this.treeLoading = true;
        findAuthorityList().then(resp => {
          if (resp.code === 200) {
            _this.authorityData = resp.data;
            _this.treeLoading = false;
          }
        }).catch(() => {
          _this.treeLoading = false;
        });
      },
      getHasAuthority(id) {
        let _this = this;
        _this.formLoading = true;
        findAuthorityById(id).then(resp => {
          _this.$refs.authorityTree.setCheckedKeys([]);
          if (resp.code === 200 && resp.data.length > 0) {
            let hasAuthorities = [];
            resp.data.forEach(item => {
              hasAuthorities.push(item)
            });
            _this.$refs.authorityTree.setCheckedKeys(hasAuthorities);
            _this.formLoading = false;
          }
        }).catch(() => {
          _this.formLoading = false;
        });
      },
      openEdit(id) {
        let _this = this;
        _this.dialogVisible = true;
        _this.isAdd = true;
        _this.getAuthorityList();
        if (id) {
          _this.formLoading = true;
          _this.isAdd = false;
          _this.getHasAuthority(id);
          findById(id).then(resp => {
            if (resp.code === 200) {
              _this.formLoading = false;
              _this.openForm = resp.data;
            }
          }).catch(() => {
            _this.formLoading = false;
          })
        }
      },
      submitEdit(formName) {
        let _this = this;
        _this.formLoading = true;
        let authorities = this.$refs.authorityTree.getCheckedKeys();
        let param = [];
        if (authorities.length > 0) {
          authorities.forEach(authorityId => {
            param.push({id: authorityId})
          })
        }
        _this.openForm.authorities = param;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            if (_this.isAdd) {
              save(_this.openForm).then(resp => {
                if (resp.code === 200) {
                  _this.formLoading = false;
                  _this.dialogVisible = false;
                  _this.$message({type: 'success', message: '添加成功'})
                  //_this.loadTable();
                }
              }).catch(() => {
                _this.formLoading = false;
              })
            } else {
              update(_this.openForm).then(resp => {
                if (resp.code === 200) {
                  _this.formLoading = false;
                  _this.dialogVisible = false;
                  _this.$message({type: 'success', message: '修改成功'})
                  _this.loadTable();
                }
              }).catch(() => {
                _this.formLoading = false;
              })
            }
          } else {
            return false;
          }
        });
      },
      deleteRoleById(id) {
        let _this = this;
        _this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
          deleteRole(id).then(resp => {
            if (resp.code === 200) {
              _this.loading = false;
              _this.loadTable();
              _this.$message({type: 'success', message: '删除成功'})
            }
          }).catch(() => {
            _this.loading = false;
          })
        }).catch(() => {
          _this.loading = false;
        });

      },
      resetFields(formName) {
        this.$refs[formName].resetFields();
        this.addFormVisible = false;
        this.dialogVisible = false;
      },
      loadTable(params) {
        let _this = this;
        _this.loading = true;
        findAll(params).then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.tableData = resp.data;
            _this.total = resp.count;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      handleSizeChange(val) {
        this.loadTable({'limit': val})
      },
      handleCurrentChange(val) {
        this.loadTable({'page': val})
      }
    }
  }
</script>

<style scoped>

</style>
