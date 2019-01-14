<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <sidebar class="sidebar-container"/>
    <div class="main-container">
      <div class="nav-bar">
        <navbar @resetPwdShow="resetPassword"/>
        <tags-view/>
      </div>
      <app-main/>
    </div>

    <!-- 修改密码 -->
    <!-- 写在这里是因为修改 nav-bar 为固定布局 fixed 后，对话框显示在 nav-bar 组件中，index 没有作用，对话框无法点击 -->
    <div class="all-dialog" v-if="centerDialogVisible">
      <el-dialog
        title="修改密码"
        :visible.sync="centerDialogVisible"
        width="30%"
        :close-on-click-modal="false"
        :before-close="handleClose"
        >
        <div style="margin-top:20px;">
          <el-form status-icon :label-position="labelPosition"  :model="formLabelAlign" :rules="rules" ref="formLabelAlign" label-width="140px" size="small">
            <el-form-item label="输入原密码：" prop="oldPwd">
              <el-input type="password" v-model="formLabelAlign.oldPwd" ></el-input>
            </el-form-item>
            <el-form-item label="输入新密码：" prop="newPwd">
              <el-input type="password" v-model="formLabelAlign.newPwd"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码：" prop="confirmPwd">
              <el-input type="password" v-model="formLabelAlign.confirmPwd"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancleForm('formLabelAlign')" size="small">取 消</el-button>
          <el-button type="primary" @click="submitForm('formLabelAlign')" size="small">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { changePwd } from '@/bin/api/person'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
    TagsView
  },
  data() {
    // 修改密码，密码验证
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
        return
      } else if (value.length < 5) {
        callback(new Error('密码长度至少5位'));
        return
      } else {
        if (this.formLabelAlign.confirmPwd !== '') {
          this.$refs.formLabelAlign.validateField('confirmPwd');
        }
        callback();
        return
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.formLabelAlign.newPwd) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      centerDialogVisible: false,
      labelPosition: 'left',
      formLabelAlign: {
        oldPwd: '',
        newPwd: '',
        confirmPwd: ''
      },
      rules: {
        oldPwd: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 5, max: 20, message: '密码长度至少5位', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        confirmPwd: [
          { required: true, validator: validatePass2, trigger: 'blur' },
        ]
      }
    }
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    },

    // 修改密码
    resetPassword() {
      this.centerDialogVisible = true
    },

    // 修改密码，提交表单
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            changePwd(this.formLabelAlign).then((res) => {
              this.centerDialogVisible = false
              this.$message({
                message: '密码修改成功',
                type: 'success',
                center:true
              })
              // 重置密码输入框
              this.cancleForm('formLabelAlign')
            }).catch(err => {
              return false
            })
          } else {
            return false;
          }
        });
      },

      // 取消修改密码
      cancleForm(formName) {
        this.$refs[formName].resetFields()
        this.centerDialogVisible = false
      },

      // 点击对话框 关闭按钮执行重置操作
      handleClose(done) {
        this.cancleForm('formLabelAlign')
      }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
</style>
