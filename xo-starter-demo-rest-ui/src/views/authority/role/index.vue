<template>
  <div id="roles" style="padding:20px;">
    <el-row :gutter="10">
      <el-col :span="6">
        <div class="role-content role-list">
          <div class="list-header">
            <span class="title">角色列表</span>
            <span class="add-role">
              <el-button type="primary" size="mini" @click="addDialogVisible = true">新增角色</el-button>
            </span>
          </div>
          <ul class="list-body">
            <li v-for="(item, index) in rolesList" :key="index" :class="activeIndex == index ? 'active' : ''" @click="toggleRole(item.id, index)">{{item.name}}</li>
          </ul>
        </div>
      </el-col>
      <el-col :span="18">
        <div class="role-content role-info">
            <div class="info-header">
              <div class="role-name">
                <el-form :model="ruleForm" :label-position="labelPosition" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                  <el-form-item label="名称：" prop="name">
                    <div class="name-input">
                      <el-input v-model="ruleForm.name" size="mini" placeholder="请输入角色名称"></el-input>
                    </div>
                  </el-form-item>
                </el-form>
              </div>
              <div class="save">
                <el-button type="primary" size="mini" @click="saveInfo('ruleForm')">保存</el-button>
              </div>
            </div>
            <div class="info-body">
                <h4>角色权限：</h4>
                <ul class="info-permission">
                  <li>
                    <p>机构：</p>
                    <p>
                      <el-checkbox v-model="checkList.organ">管理</el-checkbox>
                    </p>
                  </li>
                  <li>
                    <p>角色：</p>
                    <p>
                      <el-checkbox v-model="checkList.role">管理</el-checkbox>
                    </p>
                  </li>
                  <li>
                    <p>用户：</p>
                    <p>
                      <el-checkbox v-model="checkList.user">管理</el-checkbox>
                    </p>
                  </li>
                  <li>
                    <p>日志：</p>
                    <p>
                      <el-checkbox v-model="checkList.log">查看</el-checkbox>
                    </p>
                  </li>
                  <li>
                    <p>索引：</p>
                    <p>
                      <el-checkbox v-model="checkList.index">管理</el-checkbox>
                    </p>
                  </li>
                  <li>
                    <p>缓存：</p>
                    <p>
                      <el-checkbox v-model="checkList.cache">管理</el-checkbox>
                    </p>
                  </li>
                </ul>
            </div>
        </div>
      </el-col>
    </el-row>
    <div class="add-role-dialog all-dialog" style="padding:0; border:0;" v-if="addDialogVisible">
      <el-dialog
        title="新增角色"
        :visible.sync="addDialogVisible"
        width="40%"
        :before-close="closeDialog"
        :close-on-click-modal="false"
        >
        <div class="info-header" style="padding-top:20px;">
          <div class="role-name">
            <el-form :model="ruleForm" :label-position="labelPosition" :rules="rules" ref="addRole" label-width="100px" class="demo-ruleForm">
              <el-form-item label="名称：" prop="newName">
                <div class="name-input">
                  <el-input v-model="ruleForm.newName" size="mini" placeholder="请输入角色名称"></el-input>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="info-body">
            <h4>角色权限：</h4>
            <ul class="info-permission">
              <li>
                <p>机构：</p>
                <p>
                  <el-checkbox v-model="permissionList.organ">管理</el-checkbox>
                </p>
              </li>
              <li>
                <p>角色：</p>
                <p>
                  <el-checkbox v-model="permissionList.role">管理</el-checkbox>
                </p>
              </li>
              <li>
                <p>用户：</p>
                <p>
                  <el-checkbox v-model="permissionList.user">管理</el-checkbox>
                </p>
              </li>
              <li>
                <p>日志：</p>
                <p>
                  <el-checkbox v-model="permissionList.log">查看</el-checkbox>
                </p>
              </li>
              <li>
                <p>索引：</p>
                <p>
                  <el-checkbox v-model="permissionList.index">管理</el-checkbox>
                </p>
              </li>
              <li>
                <p>缓存：</p>
                <p>
                  <el-checkbox v-model="permissionList.cache">管理</el-checkbox>
                </p>
              </li>
            </ul>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="closeDialog">取 消</el-button>
          <el-button size="small" type="primary" @click="addNewRole('addRole')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getRoleList, addRole, updateRole, getRoleInfo } from '@/bin/api/roles'

export default {
  name: 'Roles',
  data() {
    return {
      id: '',
      rolesList: [],                      // 角色列表
      activeIndex: 0,
      addDialogVisible: false,
      labelPosition: 'left',
      // 验证规则
      ruleForm: {
        name: '',
        newName: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
        ],
        newName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
        ]
      },

      // 权限控制
      checkList: {
        organ: false,
        role: false,
        user: false,
        log: false,
        index :false,
        cache: false
      },

      // 新增用户权限
      permissionList: {
        organ: false,
        role: false,
        user: false,
        log: false,
        index :false,
        cache: false
      }
    }
  },
  mounted() {
    this.initData()
  },
  methods: {
    // 初始化数据
    initData() {
      getRoleList().then(res => {
        this.rolesList = res.data

        // 获取角色信息
        this.getInfo(this.rolesList[0].id)
      })
    },

    // 查看角色
    toggleRole(id, index) {
      this.activeIndex = index
      this.getInfo(id)
    },

    // 获取角色信息
    getInfo(id) {
      getRoleInfo(id).then(res => {
        this.id = res.data.id
        this.ruleForm.name = res.data.name
        let privilegs = res.data.privilegs
        let checkList = {
          organ: false,
          role: false,
          user: false,
          log: false,
          index :false,
          cache: false
        }
        privilegs.map(item => {
          checkList[item.split(':')[0]] = true
          this.checkList = checkList
        })
      })
    },

    // 保存编辑
    saveInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 将权限数据转为后台需要的格式
          let privilegs = this.changeData(this.checkList)

          // 上传
          let params = {
            id: this.id,
            name: this.ruleForm.name,
            privilegs: privilegs
          }
          updateRole(params).then(res => {
            this.$message({
              message: '编辑角色成功！',
              type: 'success',
              center:true
            })
          })
        } else {
          return false;
        }
      });
    },

    // 新增用户
    addNewRole(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 将权限数据转为后台需要的格式
          let privilegs = this.changeData(this.permissionList)
          if(privilegs.length === 0) {
            this.$message({
              message: '请选择其中一项权限！',
              center:true
            })
            return false
          }
          // 上传
          let params = {
            name: this.ruleForm.newName,
            privilegs: privilegs
          }
          console.log(params)
          addRole(params).then(res => {
            this.$message({
              message: '添加角色成功！',
              type: 'success',
              center:true
            })
            this.addDialogVisible = false
            this.initData()
          })
        } else {
          return false;
        }
      });
    },

    // 取消新增角色
    closeDialog(formName) {
      this.addDialogVisible = false
      this.$refs['addRole'].resetFields();
      this.permissionList = {
        organ: false,
        role: false,
        user: false,
        log: false,
        index :false,
        cache: false
      }
    },

    // 将权限数据转为后台需要的格式
    changeData(data) {
      let privilegsList = []
      for(let k in data) {
        if(data[k]){
          let privilegs = ''
          if(k === 'log'){
            privilegs = k + ':view'
          } else {
            privilegs = k + ':manage'
          }
          privilegsList.push(privilegs)
        }
      }
      return privilegsList
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  ul,li{
    padding: 0;
    list-style: none;
    margin: 0;
  }
  .active {
    background: #f9f9f9;
  }
  .role-content {
    min-height: calc(100vh - 124px);
  }
  .role-list {
    border:1px solid #f0f0f0;
    .list-header {
      padding: 5px 10px;
      line-height: 30px;
      position: relative;
      border-bottom: 1px solid #f0f0f0;
      overflow: hidden;
      .title {
        float: left;
      }
      .add-role {
        float:right
      }
    }
    .list-body {
      padding: 5px 0;
      li {
        color: #606266;
        font-size: 14px;
        padding: 10px 15px;
        cursor: pointer;
        &:hover {
          background: #f9f9f9;
        }
      }
    }
  }
  .role-info,
  .add-role-dialog {
    padding: 10px;
    border:1px solid #f0f0f0;
    .info-header {
      overflow: hidden;
      border-bottom: 1px solid #f0f0f0;
      .role-name {
        float: left;
      }
      .save {
        width:10%;
        float: right;
        text-align: right;
      }
    }
    .info-body {
      border: 1px solid #f0f0f0;
      margin-top: 20px;
      h4 {
        font-weight: 100;
        font-size: 16px;
        color: #606266;
        margin: 0;
        padding: 15px ;
        border-bottom: 1px solid #f0f0f0;
      }
      .info-permission {
        color: #606266;
        li {
          padding:15px 20px;
          overflow: hidden;
          border-bottom: 1px solid #f0f0f0;
          p {
            float: left;
            margin: 0;
            &:first-child {
              width: 10%;
            }
            &:last-child {
              width:90%;
            }
          }
          &:last-child {
            border: none;
          }
        }
      }
    }
  }
</style>
