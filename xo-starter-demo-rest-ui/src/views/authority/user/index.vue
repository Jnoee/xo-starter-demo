<template>
  <div id="users">
    <div class="users-header clearfix">
      <div class="users-header-left left">
        <el-button type="primary" size="mini" @click="addDialogShow">新增</el-button>
      </div>
      <div class="users-header-right right">
        <el-input placeholder="请输入内容" v-model="searchVal" class="input-with-select" size="small">
          <el-button slot="append" icon="el-icon-search" size="small" @click="searchList"></el-button>
        </el-input>
      </div>
    </div>
    <div class="users-body">
      <el-table
        :data="userList"
        border
        size="medium"
        height="100%"
        style="width: 100%">
        <el-table-column
          prop="username"
          label="用户名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="defaultActor"
          min-width="200"
          label="默认职务">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="170">
        </el-table-column>
        <el-table-column
          prop="createUser"
          label="创建人"
          width="130">
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="修改时间"
          width="170">
        </el-table-column>
        <el-table-column
          prop="updateUser"
          label="修改人"
          width="130">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="100">
          <template slot-scope="scope">
            <span style="color:green" v-if="scope.row.status == 1">启用</span>
            <span style="color:gray" v-else="scope.row.status == 0">停用</span>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="230">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editDialogShow(scope.row)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.status == 1" @click="blockUp(scope.row.id)">停用</el-button>
            <el-button type="text" size="small" v-else="scope.row.status == 0" @click="startUp(scope.row.id)">启用</el-button>
            <el-button type="text" size="small" @click="resetDialogShow(scope.row)">重置密码</el-button>
            <el-button type="text" size="small" @click="actorDialogShow(scope.row.id)">职务管理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- pagination -->
    <div class="user-pagination">
      <div class="block">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[15, 20, 30, 50, 80, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalNum">
        </el-pagination>
      </div>
    </div>

    <!-- 新增用户 -->
    <add-user ref='addDialog'  @refreshAddList='initData'/>
    <!-- 编辑用户 -->
    <edit-user ref='editDialog' @refreshList='initData'/>
    <!-- 重置密码 -->
    <reset-password ref='resetDialog'/>
    <!-- 职务管理 -->
    <actor ref='actorDialog'/>

  </div>
</template>
<script>
import Actor from './components/actor'
import AddUser from './components/addUser'
import EditUser from './components/editUser'
import ResetPassword from './components/resetPassword'
import {
  getUserList,
  addUser,
  updateUser,
  disableUser,
  enableUser,
  changePwd,
  getUserInfo,
  getActorList
} from '@/bin/api/users'
import { changeTime } from '@/bin/utils/index'
export default {
  name: "Users",
  components:{
    Actor,
    AddUser,
    EditUser,
    ResetPassword,
  },
  data() {
    return {
      // 列表数据
      userList: [],
      searchVal: '',    // 关键字
      // 参数
      params:{
        'keyword': '',
        'orderBy': '',
        'pageNum': 1,
        'pageSize': 15,
        'sort': ''
      },
      // 分页
      pageNum: 1,
      totalNum: 0,
      pageSize: 15,
      userName: '',
      actorid:''
    }
  },
  mounted(){
    // 初始化数据
    this.initData();
  },
  methods: {
    initData(){
      getUserList (this.params).then(res => {
        let data = res.data
        // 设置分页数据
        this.pageNum = data.number
        this.pageSize = data.size
        this.totalNum = data.count
        // 处理列表数据
        this.userList = data.contents
        data.contents.map((item,index) => {
          this.userList[index].createTime = changeTime(item.createTime)
          this.userList[index].updateTime = changeTime(item.updateTime)
        })
      }).catch(err => {
        console.log(err)
      })
    },
    // 停用
    blockUp(id) {
      disableUser(id).then(res => {
        this.$confirm('您确定要停用该用户吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '停用用户成功!',
            center: true
          });
          this.initData();
        }).catch(() => {
          return false
        })
      }).catch(err => {
        return false
      })
    },

    // 启用
    startUp(id) {
      enableUser(id).then(res => {
        this.$confirm('您确定要启用该用户吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '启用用户成功!',
            center: true
          });
          this.initData();
        }).catch(() => {
          return false
        })
      }).catch(err => {
        return false
      })
    },

    addDialogShow(){
      this.$refs.addDialog.show()
    },
    editDialogShow(row){
      this.$refs.editDialog.show(row)
    },
    resetDialogShow(row){
      this.$refs.resetDialog.show(row)
    },
    actorDialogShow(id){
      this.$refs.actorDialog.show(id)
    },

    // 搜索
    searchList(){
      this.params.keyword = this.searchVal
      this.initData()
    },

    // 选择分页数量
    handleSizeChange(val) {
      this.params.pageSize = val
      this.initData()
    },

    // 跳转页数
    handleCurrentChange(val) {
      this.params.pageNum = val
      this.initData()
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
#users {
  padding: 20px;
  height: calc(100vh - 84px);
  p {
  margin: 0;
    div {
      margin-bottom:0;
    }
  }

  .users-body {
    margin-top: 10px;
    height: 86.1%;
    overflow: auto
  }

  .user-pagination {
    position: absolute;
    left:0;
    bottom: 0px;
    z-index: 9;
    width: 100%;
    padding: 8px 0;
    background: #F9F9F9;
    .block {
      text-align: center;
    }
  }

  .tips {
    span {
      color: #d00;
    }
  }

}

</style>


