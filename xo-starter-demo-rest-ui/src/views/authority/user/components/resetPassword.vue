<template>
  <div class="reset-password all-dialog" v-if="resetPwdVisible">
    <el-dialog
      title="重置密码"
      :visible.sync="resetPwdVisible"
      :close-on-click-modal="false"
      :before-close="closeDialog"
      width="570px"
    >
      <div class="reset-password-con">
        <el-form
          :model="resetPwd"
          label-position="left"
          :rules="rules"
          ref="resetPwd"
          label-width="130px"
          class="ruleForm user-form clearfix"
        >
          <p>
            <el-form-item style="margin-bottom: 5px" label="待重置的用户名：">{{resetUserName}}</el-form-item>
          </p>
          <p>
            <el-form-item style="margin-bottom: 5px" label="待重置用户姓名：">{{resetName}}</el-form-item>
          </p>
          <p>
            <el-form-item style="margin-bottom: 5px" label="管理员密码：" prop="managePassword">
              <el-input
                type="password"
                v-model="resetPwd.managePassword"
                size="small"
                placeholder="请输入密码"
              ></el-input>
            </el-form-item>
          </p>
        </el-form>
        <div class="line"></div>
        <p class="tips">
          请输入管理员密码并点击确定，用户的密码将被重置为：
          <span>666666</span>。
        </p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="resetForm('resetPwd')">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm('resetPwd')">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { resetPwd } from "@/bin/api/users";
export default {
  name: "resetPassword",
  data() {
    return {
      rules: {
        managePassword: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      resetUserName: "",
      resetName: "",
      resetPwdVisible: false,
      resetPwd: {
        id: "",
        managePassword: ""
      }
    };
  },
  methods: {
    show(reset) {
      this.resetPwdVisible = true;
      this.resetPwd.id = reset.id;
      this.resetName = reset.name;
      this.resetUserName = reset.username;
    },
    closeDialog() {
      this.resetForm('resetPwd')
    },
    resetForm(formName) {
      this.resetPwdVisible = false;
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          resetPwd(this.resetPwd).then(res => {
            this.$message({
              type: "success",
              message: "重置密码成功!",
              center: true
            })
            this.resetForm(formName)
          })
        } else {
          return false;
        }
      })
      
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.line {
  width: 100%;
  height: 1px;
  background: #e0e0e0;
  margin: 20px 0;
}

.tips {
  span {
    color: #d00;
  }
}
</style>


