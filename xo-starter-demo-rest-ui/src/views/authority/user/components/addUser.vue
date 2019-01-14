<template>
  <div class="add-user all-dialog" v-if="dialogVisible">
    <el-dialog title="新增用户" :visible.sync="dialogVisible" :close-on-click-modal="false" width="650px">
      <div class="add-user-dialog-con">
        <el-form
          :model="addUser"
          :rules="rules"
          ref="addUser"
          label-width="100px"
          class="ruleForm user-form clearfix"
        >
          <div class="user-info clearfix">
            <h4>用户信息</h4>
            <div class="left user-info-option">
              <el-form-item label="用户名：" prop="username">
                <el-input v-model="addUser.username" size="small" placeholder="请输入用户名"></el-input>
              </el-form-item>
            </div>
            <div class="left user-info-option">
              <el-form-item label="姓名：" prop="name">
                <el-input v-model="addUser.name" size="small" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </div>
            <div class="left user-info-option">
              <el-form-item label="序号：" prop="ordinal">
                <el-input v-model.number="addUser.ordinal" size="small" placeholder="请输入序号"></el-input>
              </el-form-item>
            </div>
          </div>
          <div class="line"></div>
          <div class="user-duty clearfix">
            <h4>默认职务</h4>
            <div class="left user-info-option">
              <el-form-item label="关联机构：">
                <el-select filterable v-model="addUser.organId" style="width:184px;" size="small" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in organIdList"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  >
                    <span style="float: left;margin-right:5px;" v-for="(item, index) in item.treeLevel" :key="index"> &gt; </span>
                    <span style="float: left; font-size: 13px">{{ item.name }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="left user-info-option">
              <el-form-item label="关联角色：">
                <el-select filterable v-model="addUser.roleId" style="width:184px;" size="small" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in roleIdList"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
            <div class="left user-info-option">
              <el-form-item label="职务名称：" prop="actorName">
                <el-input v-model="addUser.actorName" size="small" placeholder="请输入职务名称"></el-input>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="resetForm('addUser')">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm('addUser')">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getOrgansTree } from "@/bin/api/organ";
import { getRoleList } from "@/bin/api/roles";
import { addUser } from "@/bin/api/users";
import { getParentOrganList } from "@/bin/utils/index";
import { checkOrdinal } from '@/bin/utils/validate'

export default {
  name: "addUser",
  data() {
    return {
      // 窗口显示隐藏
      dialogVisible: false,
      organIdList: [],
      roleIdList: [],
      // 传值给后台

      addUser: {
        actorName: "",
        name: "",
        organId: "",
        roleId: "",
        username: "",
        ordinal: ""
      },
      // 输入框验证
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        ordinal: [
          { validator: checkOrdinal, trigger: 'blur', required: true }
        ],
        actorName: [
          { required: true, message: "请输入职务名称", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    // 取消重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false;
    },
    show() {
      this.dialogVisible = true;
      // 获取关联机构
      getOrgansTree().then(res => {
        let dataChild = res.data;
        let dataFirst = res.data.id;

        this.organIdList = [];
        this.organIdList.push(dataChild);
        this.organIdList = getParentOrganList(this.organIdList);
        this.addUser.organId = dataFirst;
      });
      // 获取关联角色
      getRoleList().then(res => {
        let role = res.data;
        let roleId = res.data[0].id;
        this.roleIdList = role;
        this.addUser.roleId = roleId;
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addUser(this.addUser).then(res => {
            this.dialogVisible = false;
            this.$refs[formName].resetFields();
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            this.$emit("refreshAddList");
          });
        } else {
          console.log("请将 * 的内容填完!!");
          return false;
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.line{
  height: 1px;
  background: #f0f0f0;
}
</style>


