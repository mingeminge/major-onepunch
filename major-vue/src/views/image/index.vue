<template>
  <div class="app-container">
    <div>
      <el-form inline>
        <el-form-item label="种类">
          <el-select v-model="query.type">
            <el-option v-for="item in types" :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button class="el-icon-search" type="primary" @click="search">查询</el-button>
          <el-button @click="uploadVisible=true" class="el-icon-upload" v-has="['sysImage:upload']" type="primary">上传
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="20" v-loading="loading">
      <el-col v-for="item in images" :key="item.id" :span="4" :xs="12" class="image">
        <el-card :body-style="{ padding: '0px' }">
          <el-image fit="scale-down" style="height: 150px" :src="IMG_URL+item.imgUrl"
                    lazy></el-image>
          <div>
            <span>{{item.description||'未命名'}}</span>
            <div class="bottom clearfix">
              <el-button type="text" class="el-icon-edit" @click="openImg(IMG_URL+item.imgUrl)">查看
              </el-button>
              <el-button type="text" class="el-icon-edit" v-has="['sysImage:update']" @click="openUpdate(item)">修改
              </el-button>
              <el-button type="text" class="el-icon-delete" v-has="['sysImage:delete']" @click="deleteById(item.id)">
                删除
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :width="dialogWidth" title="头像上传" :visible.sync="uploadVisible">
      <el-form v-loading="dialogLoading" :model="image" ref="uploadForm" label-position="left" label-width="70px">
        <el-form-item label="描述" prop="description">
          <el-input v-model="image.description"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="type">
          <el-select v-model="image.type">
            <el-option v-for="item in types" :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择图片" prop="imgUrl">
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="uploadAction"
            :headers="uploadHeader"
            :on-success="uploadSuccess"
            :multiple="false"
            list-type="picture"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button class="el-icon-upload-success" type="success" @click="submitUpload">上传</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog :width="dialogWidth" title="头像编辑" :visible.sync="updateVisible">
      <el-form v-loading="dialogLoading" :model="updateImg" ref="updateForm" label-position="left" label-width="70px">
        <el-form-item label="描述" prop="description">
          <el-input v-model="updateImg.description"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="type">
          <el-select v-model="updateImg.type">
            <el-option v-for="item in types" :label="item.label" :value="item.value" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="el-icon-success" @click="update">保存</el-button>
          <el-button class="el-icon-error" @click="updateVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[24, 48, 96, 192]"
      :page-size="24"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-dialog center :top="dialogTop" :width="dialogWidth" :visible.sync="imgVisible">
      <el-image style="width: 100%;" :src="imgSrc"></el-image>
    </el-dialog>
  </div>
</template>

<script>
  import {deleteImg, findAll, save, updateImg} from "@/api/image";
  import {isMobile} from "@/utils/index";

  export default {
    name: "index",
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        uploadAction: process.env.BASE_API + '/image/upload',
        uploadHeader: {'token': localStorage.token},
        fileList: [],
        loading: true,
        dialogLoading: false,
        query: {page: 1, limit: 24, type: ''},
        types: [{label: '', value: ''}, {label: '英雄', value: 1},
          {label: '怪人', value: 2}, {label: '其他', value: 0}],
        images: [],
        total: 0,
        uploadVisible: false,
        updateVisible: false,
        image: {},
        updateImg: {},
        imgSrc: '',
        imgVisible: false
      }
    },
    computed: {
      dialogWidth() {
        if (isMobile()) {
          return '90%'
        } else {
          return '50%'
        }
      },
      dialogTop(){
        if (isMobile()) {
          return '15vh'
        } else {
          return '1vh'
        }
      }
    },
    mounted() {
      this.getImages(this.query);
    },
    methods: {
      search() {
        this.getImages(this.query);
      },
      update() {
        let _this = this;
        _this.dialogLoading = true;
        updateImg(_this.updateImg).then(resp => {
          if (resp.code === 200) {
            _this.dialogLoading = false;
            _this.updateVisible = false;
            _this.$refs['updateForm'].resetFields();
            _this.$message.success('修改成功!');
            _this.getImages(_this.query);
          }
        }).catch(() => {
          _this.dialogLoading = false;
          _this.updateVisible = false;
        })
      },
      openUpdate(item) {
        this.updateVisible = true;
        this.updateImg = item;
      },
      deleteById(id) {
        let _this = this;
        _this.$confirm('是否删除该头像?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.dialogLoading = true;
          deleteImg(id).then(resp => {
            if (resp.code === 200) {
              _this.dialogLoading = false;
              _this.$message.success('删除成功');
              _this.getImages(_this.query);
            }
          }).catch(() => {
            _this.dialogLoading = false;
          })
        });
      },
      getImages(params) {
        let _this = this;
        _this.loading = true;
        findAll(params).then(resp => {
          if (resp.code === 200) {
            _this.images = resp.data;
            _this.total = resp.count;
            _this.loading = false;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
      uploadSuccess(response, file, fileList) {
        let _this = this;
        if (response.code === 200) {
          _this.dialogLoading = true;
          _this.image.imgUrl = response.data;
          save(_this.image).then(resp => {
            if (resp.code === 200) {
              _this.fileList = [];
              _this.dialogLoading = false;
              _this.uploadVisible = false;
              _this.$refs['uploadForm'].resetFields();
              _this.$message.success('上传成功!')
              _this.getImages(_this.query);
            }
          }).catch(() => {
            _this.fileList = [];
            _this.dialogLoading = false;
            _this.uploadVisible = false;
            _this.$refs['uploadForm'].resetFields();
          })
        }
      },
      handleSizeChange(val) {
        this.query.limit = val;
        this.getImages(this.query)
      },
      handleCurrentChange(val) {
        this.query.page = val;
        this.getImages(this.query)
      },
      openImg(imgSrc) {
        this.imgVisible = true;
        this.imgSrc = imgSrc;
      }
    }
  }
</script>

<style scoped>
  .image {
    margin-bottom: 10px;
    text-align: center;
    font-size: small;
    color: rgba(0, 0, 0, .45);
  }
</style>
