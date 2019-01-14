<template>
  <div class="edit-Actor">
    <el-dialog class="all-dialog" title="职务管理" :visible.sync="actorVisible" :close-on-click-modal="false" width="768px">
      <div class="users-body">
        <el-button
          type="primary"
          plain
          size="mini"
          icon="el-icon-circle-plus-outline"
          @click="getActorTree"
        >新增</el-button>
      </div>
      <div class="users-body">
        <el-table :data="actor.actorList" border style="width: 100%;">
          <el-table-column prop="name" label="职务名称"></el-table-column>
          <el-table-column prop="organName" label="关联机构"></el-table-column>
          <el-table-column prop="roleName" label="关联角色"></el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="editActor(scope.row)">编辑</el-button>
              <el-button type="text" size="small" v-show="!scope.row.defaulted" @click="deleteActor(scope.row)">删除</el-button>
              <el-button type="text" size="small" v-show="!scope.row.defaulted" @click="setDefault(scope.row.id)">设为默认职务</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 新增职务和编辑职务 -->
      <div v-if="addVisible">
        <el-dialog class="all-dialog" width="450px" :title="dialogTitle" :visible.sync="addVisible" :close-on-click-modal="false" :before-close="closeAddDialog" append-to-body>
          <el-form class="addActorForm" :model="actor" ref="addActor" label-position="right" :rules="rules">

            <!-- 关联机构 -->
            <el-form-item label="关联机构：" label-width="100px">
              <el-select filterable v-model="params.organId" placeholder="请选择" size="small" @change="selectOrganId">
                <el-option
                  v-for="(item,index) in actor.organTreeList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                >
                  <span
                    style="float: left;margin-right:5px;"
                    v-for="(option, index) in item.treeLevel" :key="index"
                  >&gt;</span>
                  <span style="float: left; font-size: 13px">{{ item.name }}</span>
                </el-option>
              </el-select>
            </el-form-item>

            <!-- 关联角色 -->
            <el-form-item label="关联角色：" label-width="100px">
              <el-select filterable v-model="params.roleId" placeholder="请选择" size="small" @change="selectRoleId">
                <el-option
                  v-for="(item,index) in actor.roleTreeList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>

            <!-- 职务名称 -->
            <el-form-item label="职务名称：" label-width="100px" prop="name">
              <el-input v-model="actor.name" style="width:199px" placeholder="请输入职务名称" size="small"></el-input>
            </el-form-item>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="resetForm('addActor')">取消</el-button>
            <el-button size="small" type="primary" @click="submitAddActor('addActor')">保存</el-button>
          </div>
        </el-dialog>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import { getOrgansTree } from "@/bin/api/organ";
import { getRoleList } from "@/bin/api/roles";
import { getActorList, addActor, updateActor, delActor, setDefaultActor } from "@/bin/api/users";
import { getParentOrganList } from "@/bin/utils/index";

export default {
  name: "Actor",
  data() {
    return {
      dialogTitle: '新增职务',
      actorVisible: false,
      addVisible: false,
      flag: 0,
      rules: {
        name: [
          { required: true, message: "请输入职务名称", trigger: "blur" }
        ]
      },

      actor:{
        name: '',
        actorList: [],
        roleTreeList: [],
        organTreeList: []
      },

      // 上传参数
      params: {
        id: '',
        name: '',
        userId: '',
        roleId: '',
        organId: ''
      }
    };
  },
  methods: {
    show(userId) {
      this.actorVisible = true;
      this.params.userId = userId

      // 获取列表
      this.getActorLists(this.params.userId)
    },

    // 获取职务列表
    getActorLists(id) {
      getActorList(id).then(res => {
        this.actor.actorList = res.data;
      });
    },

    getActorTree() {
      this.flag = 0
      this.addVisible = true;
      this.dialogTitle = '新增职务'

      // 获取关联机构
      getOrgansTree().then(res => {
        let treeArr = []
        treeArr.push(res.data)
        let organ = getParentOrganList(treeArr);
        this.actor.organTreeList = organ;
        this.params.organId = organ[0].id;
      });

      // 获取关联角色
      getRoleList().then(res => {
        let roleData = res.data;
        if(roleData.length > 0) {
          this.actor.roleTreeList = roleData;
          this.params.roleId = roleData[0].id;
        }
      });
    },

    // 获取机构 Id
    selectOrganId(val) {
      this.params.organId = val
    },

    // 获取角色 Id
    selectRoleId(val) {
      this.params.roleId = val
    },

    // 编辑职务
    editActor(data) {
      this.flag = 1
      this.addVisible = true;
      this.dialogTitle = '编辑职务'

      // 获取关联机构
      getOrgansTree().then(res => {
        let treeArr = []
        treeArr.push(res.data)
        let organ = getParentOrganList(treeArr);
        this.actor.organTreeList = organ;

        this.params.organId = organ[0].id;
        organ.map((item,index) => {
          if(item.name === data.organName) {
            this.params.organId = item.id;
          }
        })
      });

      // 获取关联角色
      getRoleList().then(res => {
        let roleData = res.data;
        this.actor.name = data.name
        this.actor.roleTreeList = roleData;

        this.params.id = data.id
        roleData.map(item => {
          if(item.name == data.roleName) {
            this.params.roleId = item.id;
          }
        })
      });

    },

    // 删除职务
    deleteActor(data) {
      this.params.id = data.id
      delActor(this.params).then(res => {
        this.$confirm('你确定要删除该职务吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除职务成功!',
            center: true
          });
          this.getActorLists(this.params.userId)
          this.updateStore()
        }).catch(() => {
          return false
        })
      }).catch(err => {
        return false
      })
    },

    // 设为默认职务
    setDefault(id) {
      setDefaultActor(id).then(res => {
        this.$confirm('你确定要将该职务设置为默认职务吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '设置默认职务成功!',
            center: true
          });
          this.getActorLists(this.params.userId)
        }).catch(() => {
          return false
        })
      })
    },

    // 关闭时 重置对话框 
    closeAddDialog() {
      this.resetForm('addActor')
    },
    resetForm(formName) {
      if(formName === 'addActor') {
        this.addVisible = false;
      }
      this.$refs[formName].resetFields();
    },

    // 更新 VUEX
    updateStore() {
      this.$store.dispatch('getActorLists').then(res => {
      })
    },

    // 保存
    submitAddActor(formName) {
      if(this.flag === 0) {
        // 新增机构
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.params.name = this.actor.name
            addActor(this.params).then(res => {
              this.$message({
                type: "success",
                message: "新增职务成功!",
                center: true
              })
              this.getActorLists(this.params.userId)
              this.updateStore()
              this.resetForm(formName)
            })
          } else {
            return false;
          }
        });
      }else{
        // 编辑机构
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.params.name = this.actor.name
            updateActor(this.params).then(res => {
              this.$message({
                type: "success",
                message: "编辑职务成功!",
                center: true
              })
              this.getActorLists(this.params.userId)
              this.updateStore()
              this.resetForm(formName)
            })
          } else {
            return false;
          }
        });
      }
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.users-body {
  margin-top: 10px;
}

.addActorForm {
  margin-top: 20px;
}
</style>


