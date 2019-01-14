<template>
    <div class="edit-user all-dialog" v-if="editUserVisible">
      <el-dialog
        title="编辑用户"
        :visible.sync="editUserVisible"
        :close-on-click-modal=false
        width="35%"
        >
        <div class="edit-user-dialog-con">
          <el-form :model="editUser" :rules="rules" label-position="left" ref="editUser" label-width="100px" class="ruleForm user-form clearfix">
            <div class="user-info clearfix">
                <div class="left user-info-option">
                  <el-form-item label="用户名：" prop="username">
                    <el-input v-model="editUser.username" size="small" placeholder="请输入用户名"></el-input>
                  </el-form-item>
                </div>
                <div class="left user-info-option">
                  <el-form-item label="姓名：" prop="name">
                    <el-input v-model="editUser.name" size="small" placeholder="请输入姓名"></el-input>
                  </el-form-item>
                </div>
                <div class="left user-info-option">
                  <el-form-item label="序号：" prop="ordinal">
                    <el-input v-model.number="editUser.ordinal" size="small" placeholder="请输入序号"></el-input>
                  </el-form-item>
                </div>
            </div>
            </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="resetForm('editUser')">取 消</el-button>
          <el-button size="small" type="primary" @click="submitForm('editUser')">保 存</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
import { updateUser } from '@/bin/api/users'
import { checkOrdinal } from '@/bin/utils/validate'

export default {
  name:"editUser",
  data(){
    return {
      editUserVisible: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        ordinal: [
          { validator: checkOrdinal, trigger: 'blur', required: true }
        ]
      },
      editUser: {
        id:'',
        name: '',
        ordinal: '',
        username: '',
      },
    }
  },
  methods:{
    show(data){
      this.editUserVisible = true
      this.editUser.id = data.id;
      this.editUser.name = data.name;
      this.editUser.ordinal = data.ordinal;
      this.editUser.username = data.username;
    },
    resetForm(formName) {
      this.editUserVisible = false
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateUser(this.editUser).then(res => {
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            this.$emit('refreshList')
            this.editUserVisible = false
          })
        } else {
          return false;
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .edit-user-dialog-con {
    margin-top: 20px;
  }
  .user-info-option {
    margin-right: 30px;
  }
</style>


