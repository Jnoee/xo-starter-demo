<template>
  <div class="app-container">
    <!-- 树状组件 -->
    <el-row :gutter="10">
      <el-col :span="7">
        <div class="grid-content bg-purple">
          <div class="custom-tree-container">
            <div class="block">
              <el-tree
                :data="organList"
                node-key="id"
                @node-click="handleNodeClick"
                :default-expand-all="true"
                :show-checkbox="false"
                :expand-on-click-node="false"
                :props="defaultProps"
              >
                <span class="custom-tree-node" slot-scope="{ node, data }" :style="data.status === '0' ? 'color:#CCC' : ''">
                  <span>{{ node.label }}</span>
                  <span>
                    <el-button type="text" size="mini" v-show="node.level === 1" @click.stop="dialogVisible = true">[新增机构]</el-button>
                    <el-button type="text" size="mini" v-show="data.status === '0' && node.level !== 1" @click="enableOrgan(data.id)" style="color:#d00">启用</el-button>
                    <el-button type="text" size="mini" v-show="data.status === '1' && node.level !== 1" @click="disableOrgan(data.id)">停用</el-button>
                  </span>
                </span>
              </el-tree>
            </div>
          </div>
        </div>
      </el-col>

      <!-- 详细数据 -->
      <el-col :span="17">
        <div class="grid-content bg-purple">
          <el-row style="height:63px;">
            <el-col :span="10" v-if="hasParents">
              <el-form ref="details" :label-position="labelPosition" :model="details" label-width="100px">
                <el-form-item label="上级机构：">
                  <el-select v-model="details.parentId" @change="changeTreeParent" filterable size="mini" style="width:163px;" placeholder="请输入关键字搜索">
                    <el-option
                      v-for="item in treeClass"
                      v-if="item.name !== details.name"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                     >
                      <span style="float: left;margin-right:5px;" v-for="(item, index) in item.treeLevel" :key="index"> &gt; </span>
                      <span style="float: left; font-size: 13px">{{ item.name }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="14" style="text-align:right;float:right;">
              <el-button type="primary" size="mini" style="margin:6px 0 14px;" @click="saveOrganInfo">保存</el-button>
            </el-col>
          </el-row>
          <el-row class="group group-height">
            <el-form ref="details" :label-position="labelPosition" :model="details" :rules="rules" label-width="100px">
              <el-col :span="10">
                <el-form-item label="机构名称：" prop="name">
                  <el-input v-model="details.name" size="mini" style="width:163px;" placeholder="请输入机构名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="序号：" prop="ordinal">
                  <el-input v-model.number="details.ordinal" size="mini" style="width:163px;" placeholder="请输入序号"></el-input>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
        </div>
      </el-col>
    </el-row>

    <!-- 增加机构 -->
    <div class="all-dialog" v-if="dialogVisible">
      <el-dialog
        title="新增机构"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose"
        :close-on-click-modal="false">
        <el-row style="margin-top:20px;">
          <el-col >
            <el-form ref="details" :label-position="labelPosition" :model="addOrgan" label-width="100px">
              <el-form-item label="上级机构：">
                <el-select v-model="addOrgan.parentId" @change="changeSelect" size="mini" style="width:163px;" placeholder="请选择">
                  <el-option
                    v-for="item in treeClass"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    >
                    <span style="float: left;margin-right:5px;" v-for="(option, index) in item.treeLevel" :key="index"> &gt; </span>
                    <span style="float: left; font-size: 13px">{{ item.name }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row class="group group-height">
          <el-form ref="addOrgan" :label-position="labelPosition" :model="addOrgan" :rules="rules" label-width="100px">
            <div style="float:left;width:50%">
              <el-form-item label="机构名称：" prop="name">
                <el-input v-model="addOrgan.name" size="mini" style="width:163px;" placeholder="请输入机构名称"></el-input>
              </el-form-item>
            </div>
            <div style="float:left;">
              <el-form-item label="序号：" prop="ordinal">
                <el-input v-model.number="addOrgan.ordinal" size="mini" style="width:163px;" placeholder="请输入序号"></el-input>
              </el-form-item>
            </div>
          </el-form>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="closeDialog('addOrgan')">取 消</el-button>
          <el-button type="primary" size="small" @click="confirm('addOrgan')">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  getOrgansTree,
  getOrgans,
  disableOrgans,
  enableOrgans,
  updateOrgan,
  addOrgan
} from "@/bin/api/organ";

import { checkOrdinal } from '@/bin/utils/validate'
export default {
  data() {
    return {
      // 树组件数据
      active: false,
      organId: '',
      organList: [],
      defaultProps: {
        children: 'childs',
        label: 'name',
        disabled: function(data, node) {
          if(data.status === '0' ){
            return true
          }else {
            return false
          }
        }
      },
      // 表单数据
      labelPosition: 'left',
      details: {
        id: '',
        name: '',
        ordinal: '',
        parentId: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入机构名称', trigger: 'blur' },
        ],
        ordinal:[
          { validator: checkOrdinal, trigger: 'blur', required: true }
        ]
      },

      treeClass:[],  // 上级机构数据
      level:0, // 树层级

      hasParents:false,
      dialogVisible:false,
      // 新增机构
      addOrgan: {
        name: '',
        ordinal: '',
        parentId: ''
      }
    }
  },
  created() {
    this.initData()
    this.getOrganInfo(this.organId)
  },
  methods: {
    // 初始化数据，获取机构树信息
    initData() {
      getOrgansTree().then(res => {
        let data = res.data;
        let treeArr= []
        treeArr.push(data)
        // 获取树信息
        this.level = 0
        this.organList = []
        this.treeClass = []

        this.organId = data.id

        // 重新获取新组成的树信息
        this.organList = this.getParentOrgan(treeArr)
      })
    },

    // 获取机构信息
    getOrganInfo(id) {
      getOrgans(id).then(res => {
        let data = res.data
        // 根级没有 parentId， 为空
        this.details.id = data.id
        this.details.name = data.name
        this.details.ordinal = data.ordinal

        this.addOrgan.parentId = data.id
      }).catch(err => {
        return false
      })
    },

    // 查看机构信息
    handleNodeClick(data, node) {
      // console.log(data)
      // console.log(node)
      // console.log(node.parent)
      // console.log(node.parent.data.id)

      this.details.id = data.id
      this.details.name = data.name
      this.details.ordinal = data.ordinal

      // 如果没右上级机构,parentId 为空
      if(node.parent.data.id) {
        this.details.parentId = node.parent.data.id
      } else {
        this.details.parentId = ''
      }

      // 点击根节点信息，显示隐藏上级机构选项
      if(node.level !== 1) {
         this.hasParents = true
      } else {
        this.hasParents = false
      }

      // 如果当前树节点父级为停用状态，显示根节点信息
      if(node.parent.data.status === '0'){
        this.details.parentId = this.treeClass[0].id
      }

    },

    // 停用机构
    disableOrgan(id) {
      this.$confirm('您是否确定要停用该机构?', '确认提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        disableOrgans(id).then(res => {
          this.$message({
            type: 'success',
            message: '停用机构成功!',
            center: true
          });
          this.initData()
        }).catch(err => {
          return false
        })
      }).catch(() => {
        return false
      })
    },

    // 启用机构
    enableOrgan(id) {
      this.$confirm('您是否确定要启用该机构?', '确认提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableOrgans(id).then(res => {
          this.$message({
            type: 'success',
            message: '启用机构成功!',
            center: true
          });
          this.initData()
        }).catch(err => {
          return false
        })
      }).catch(() => {
        return false
      })
    },

    // 保存机构信息
    saveOrganInfo() {
      updateOrgan(this.details).then(res => {
        console.log(res.data)
        this.$message({
          type: 'success',
          message: '编辑机构成功!',
          center: true
        });
        this.initData()
      }).catch(err => {
        return false
      })
    },

    // 获取机构id
    changeSelect(val){
      this.addOrgan.parentId = val
    },

    changeTreeParent(val) {
      this.details.parentId = val
    },

    // 提交机构信息
    confirm(formName) {
      this.$refs[formName].validate((valid) => {
          if (valid) {
            addOrgan(this.addOrgan).then(res => {
              console.log(res.data)
              this.$message({
                type: 'success',
                message: '新增机构成功!',
                center: true
              });
              this.initData()
              this.closeDialog('addOrgan')
              this.dialogVisible = false
            }).catch(err => {
              return false
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },

    // 关闭弹窗,重置输入框
    closeDialog(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
    },

    handleClose(done) {
      this.closeDialog('addOrgan')
    },

    // 根据机构树信息，获取上级机构
    getParentOrgan(organs,level = 0) {
      if(organs.length == 0) return;
      const res = []
      organs.map((item, index) => {
        if (item.status === '1') {
          // 添加树层级
          item.treeLevel = level
          this.treeClass.push(item)
          if (item.childs.length>0) {
            // 递归
            item.childs = this.getParentOrgan(item.childs, level+1)
          }
        }
        res.push(item)
      })
      return res
    }
  }
}
</script>

<style lang="scss" scoped>
.active {
  background: #f0f0f0;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
  border: solid 1px #f0f0f0;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.group {
  label {
    font-size: 14px;
    color: #606266;
  }
}
.group-bottom {
  margin-bottom: 12px;
}
.group-height {
  padding: 20px 0;
  border-top: 1px solid #f0f0f0;
}

.divider {
  clear: both;
  display: block;
  overflow: hidden;
  text-indent: -1000px;
  width: auto;
  height: 1px;
  padding: 4px 0 0 0;
  margin-bottom: 5px;
  border-style: dotted;
  border-width: 0 0 1px 0;
  border-color: #000;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.grid-content {
    padding: 10px;
    height: calc(100vh - 124px);
    background: #ffffff;
    overflow: auto;
}

</style>
