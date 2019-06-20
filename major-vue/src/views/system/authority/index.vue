<template>
  <div class="app-container">
    <el-button-group class="query-form-button">
      <el-button type="primary" class="el-icon-circle-plus-outline" v-has="['sysAuthority:add']" @click="openAdd">添加
      </el-button>
    </el-button-group>
    <el-table :data="tableData" v-loading="loading" ref="authorityTable" stripe style="width: 100%" row-key="id"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column prop="id" label="权限ID"></el-table-column>
      <el-table-column prop="authorityName" label="权限名称"></el-table-column>
      <el-table-column prop="authorityUrl" label="权限标识"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-if="scope.row.parentId===0" v-has="['sysAuthority:add']" class="el-icon-circle-plus-outline"
                       type="primary"
                       @click="openChild(scope.row.id,scope.row.authorityName)"></el-button>
            <el-button type="primary" class="el-icon-edit" v-has="['sysAuthority:update']"
                       @click="openEdit(scope.row.id)"></el-button>
            <el-button type="danger" class="el-icon-delete" v-has="['sysAuthority:delete']"
                       @click="deleteRoleById(scope.row.id)"></el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :width="dialogWidth" :visible.sync="addFormVisible" title="添加新权限">
      <el-form v-loading="formLoading" ref="addForm" :model="addForm" label-width="80px" label-position="left">
        <el-form-item label="权限名称" prop="authorityName"
                      :rules="[{ required: true, message: '请输入权限名', trigger: 'blur' }]">
          <el-input v-model="addForm.authorityName"></el-input>
        </el-form-item>
        <el-form-item v-if="addForm.parentId" label="权限标识" prop="authorityUrl"
                      :rules="[{ required: true, message: '请输入权限标识', trigger: 'blur' }]">
          <el-input v-model="addForm.authorityUrl"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitAdd('addForm')" type="primary">提交</el-button>
        <el-button @click="resetFields('addForm')">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog :width="dialogWidth" :visible.sync="editFormVisible" title="修改权限">
      <el-form v-loading="formLoading" ref="editForm" :model="editForm" label-width="80px" label-position="left">
        <el-form-item label="权限名称" prop="authorityName"
                      :rules="[{ required: true, message: '请输入权限名', trigger: 'blur' }]">
          <el-input v-model="editForm.authorityName"></el-input>
        </el-form-item>
        <!--<el-form-item  label="权限标识" prop="authorityUrl">
          <el-input v-model="openForm.authorityUrl"></el-input>
        </el-form-item>-->
        <el-form-item v-if="editForm.parentId!==0" label="权限标识" prop="authorityUrl"
                      :rules="[{ required: true, message: '请输入权限标识', trigger: 'blur' }]">
          <el-input v-model="editForm.authorityUrl"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="editForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitEdit('openForm')" type="primary">提交</el-button>
        <el-button @click="resetFields('openForm')">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog :width="dialogWidth" :visible.sync="addChildVisible" title="添加子权限">
      <el-form v-loading="formLoading" ref="addChildForm" :model="addChildForm" label-width="100px"
               label-position="left">
        <el-form-item label="父级权限名" prop="parentName">
          <el-input v-model="addChildForm.parentName"></el-input>
        </el-form-item>
        <el-form-item label="权限名称" prop="authorityName"
                      :rules="[{ required: true, message: '请输入权限名', trigger: 'blur' }]">
          <el-input v-model="addChildForm.authorityName"></el-input>
        </el-form-item>
        <el-form-item label="权限标识" prop="authorityUrl"
                      :rules="[{ required: true, message: '请输入权限标识', trigger: 'blur' }]">
          <el-input v-model="addChildForm.authorityUrl"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addChildForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitAddChild('addChildForm')" type="primary">提交</el-button>
        <el-button @click="resetFields('addChildForm')">取消</el-button>
      </div>
    </el-dialog>

    <!-- <el-dialog :width="dialogWidth" :visible.sync="dialogVisible" :title="isAdd?'添加权限':'修改权限'">
       <el-form v-loading="formLoading" ref="openForm" :model="openForm" label-width="100px"
                label-position="left">
         <el-form-item v-if="openForm.parentName" label="父级权限名" prop="parentName">
           <el-input v-model="openForm.parentName"></el-input>
         </el-form-item>
         <el-form-item label="权限名称" prop="authorityName"
                       :rules="[{ required: true, message: '请输入权限名', trigger: 'blur' }]">
           <el-input v-model="openForm.authorityName"></el-input>
         </el-form-item>
         <el-form-item v-if="openForm.parentId" label="权限标识" prop="authorityUrl"
                       :rules="[{ required: true, message: '请输入权限标识', trigger: 'blur' }]">
           <el-input v-model="openForm.authorityUrl"></el-input>
         </el-form-item>
         <el-form-item v-else label="权限标识" prop="authorityUrl">
           <el-input v-model="openForm.authorityUrl"></el-input>
         </el-form-item>
         <el-form-item label="备注" prop="remark">
           <el-input v-model="openForm.remark"></el-input>
         </el-form-item>
       </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button @click="submit('openForm')" type="primary">提交</el-button>
         <el-button @click="resetFields('openForm')">取消</el-button>
       </div>
     </el-dialog>-->
  </div>
</template>

<script>
  import {deleteById, findAll, findById, save, update} from "@/api/authority";
  import {isMobile} from "@/utils";

  export default {
    name: "Authority",
    data() {
      return {
        loading: false,
        formLoading: false,
        tableData: [],
        addForm: {},
        editForm: {},
        addChildForm: {},
        addFormVisible: false,
        editFormVisible: false,
        addChildVisible: false
      }
    },
    mounted() {
      this.loadTable();
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
    methods: {
      openChild(parentId, parentName) {
        this.addChildForm.parentName = parentName;
        this.addChildForm.parentId = parentId;
        this.addChildVisible = true;
      },
      openAdd(id) {
        this.isAdd = true;
        this.addFormVisible = true;
      },
      openEdit(id) {
        let _this = this;
        _this.formLoading = true;
        _this.editFormVisible = true;
        findById(id).then(resp => {
          if (resp.code === 200) {
            _this.formLoading = false;
            _this.editForm = resp.data;
          }
        }).catch(() => {
          _this.formLoading = false;
        })
      },

      submitAddChild(formName) {
        let _this = this;
        _this.formLoading = true;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            save(_this.addChildForm).then(resp => {
              _this.formLoading = false;
              _this.addChildVisible = false;
              _this.$message({type: 'success', message: '添加成功'})
              _this.loadTable();
            }).catch(() => {
              _this.formLoading = false;
              _this.addChildVisible = false;
            })
          } else {
            return false;
          }
        });
      },
      submitEdit(formName) {
        let _this = this;
        _this.loading = true;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            update(_this.openForm).then(resp => {
              if (resp.code === 200) {
                _this.formLoading = false;
                _this.dialogVisible = false;
                _this.$message({type: 'success', message: '修改成功'})
                _this.loadTable();
              }
            }).catch(() => {
              _this.formLoading = false;
              _this.dialogVisible = false;
            })
          } else {
            return false;
          }
        });
      },
      submitAdd(formName) {
        let _this = this;
        _this.formLoading = true;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            save(_this.addForm).then(resp => {
              if (resp.code === 200) {
                _this.formLoading = false;
                _this.addFormVisible = false;
                _this.$message({type: 'success', message: '添加成功'})
                _this.loadTable();
              }
            }).catch(() => {
              _this.addFormVisible = false;
              _this.formLoading = false;
            })
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
          _this.formLoading = true;
          deleteById(id).then(resp => {
            if (resp.code === 200) {
              _this.formLoading = false;
              _this.$message({type: 'success', message: '删除成功'})
              _this.loadTable();
            }
          }).catch(() => {
            _this.formLoading = false;
          })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
          _this.formLoading = false;
        });

      },
      resetFields(formName) {
        this.$refs[formName].resetFields();
        this.addFormVisible = false;
        this.editFormVisible = false;
      },
      loadTable() {
        let _this = this;
        _this.loading = true;
        findAll().then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.tableData = resp.data;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
    }
  }
</script>

<style scoped>

</style>
