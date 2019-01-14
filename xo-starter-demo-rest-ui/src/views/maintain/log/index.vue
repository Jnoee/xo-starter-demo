<template>
  <div id="log" style="padding:20px;">
    <!-- search -->
    <div class="log-search">
      <el-input placeholder="请输入内容" v-model="searchVal" class="input-with-select" size="small">
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>
    <!-- list -->
    <div class="log-list">
      <el-table
        border
        height="100%"
        :data="tableData"
        size="medium "
        style="width: 100%">
        <el-table-column
          fixed
          prop="operateTime"
          width="170"
          label="操作时间"
          >
        </el-table-column>
        <el-table-column
          prop="operator"
          width="150"
          label="操作用户"
          >
        </el-table-column>
        <el-table-column
          prop="message"
          label="操作内容"
          >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="详细日志"
          width="100"
          >
          <template slot-scope="scope" >
            <el-button
              v-if="scope.row.hasData"
              @click.native.prevent="showDetails(scope.row.id)"
              type="text"
              size="small">
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- pagination -->
    <div class="log-pagination">
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

    <!-- dialog -->
    <div class="log-dialog all-dialog" v-if="dialogVisible">
      <el-dialog
        title="查看详细日志"
        :visible.sync="dialogVisible"
        width="50%"
        :close-on-click-modal=false>
        <div class="log-dialog-con">
          <ul class="log-dialog-con-header clearfix">
            <li>操作时间：<span>{{logDetails.operateTime}}</span></li>
            <li>操作用户：<span>{{logDetails.operator}}</span></li>
            <li>操作内容：<span>{{logDetails.message}}</span></li>
          </ul>
          <div class="log-dialog-con-body">
            <el-table
              :data="logDetailsTable"
              border
              style="width: 100%">
              <el-table-column
                prop="name"
                label="字段"
               >
              </el-table-column>
              <el-table-column
                prop="oldData"
                label="原值"
                >
              </el-table-column>
              <el-table-column
                label="新值"
                >
                <template slot-scope="scope">
                  <span :style="scope.row.status === 0 ? 'color: #d00' : ''">{{ scope.row.newData }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>


<script>
import { getLogList, getLogDetails } from '@/bin/api/log'
import { changeTime } from '@/bin/utils/index'

export default {
  name: "Log",
  data() {
    return {
      searchVal: "",
      params:{
        "keyword": "",
        "orderBy": "",
        "pageNum": 1,
        "pageSize": 15,
        "sort": ""
      },
      tableData: [],
      pageNum: 1,
      totalNum: 0,
      pageSize: 15,
      logDetails: '',
      logDetailsTable: [],
      dialogVisible:false
    }
  },
  created(){
    this.initData();
  },
  methods: {
    initData(){
      getLogList(this.params).then(res => {
        let data = res.data
        // 分页数据
        this.pageNum = data.number
        this.pageSize = data.size
        this.totalNum = data.count
        // 列表数据
        this.tableData =  data.contents
        data.contents.map((item,index) => {
          this.tableData[index].operateTime = changeTime(item.operateTime)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    // 搜索
    search() {
      this.params.keyword = this.searchVal
      this.initData()
    },
    // 查看详情
    showDetails(id) {
      getLogDetails(id).then((res) => {
        let data = res.data
        let logDetailsTable = []
        data.operateTime = changeTime(data.operateTime)
        this.logDetails = data
        // 存放表格数据
        this.logDetailsTable = this.changeTableData(data.origData, data.newData)
        console.log(this.logDetailsTable)
        this.dialogVisible = true
      }).catch((error) => {
        console.log(error)
      })
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
    // 字段转换
    changeTableData(origData,newData) {
      let resArr = []
      let keys =  Object.keys(JSON.parse(origData))
      origData = JSON.parse(origData)
      newData = JSON.parse(newData)
      keys.map((item, index) => {
        let res  = {
          name: item,
          oldData: origData[item],
          newData: newData[item]
        }
        resArr.push(res)
      })
      resArr.map((item,index) => {
        if(item.oldData === item.newData) {
          item.status = 1
        } else {
          item.status = 0
        }
      })
      return resArr
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  #log {
    height: calc(100vh - 84px);
    position: relative;
    overflow: hidden;
    .log-list {
      margin-top:20px;
      height: 90%;
      overflow: auto;
      padding-bottom: 32px;
    }
    .log-pagination {
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
    .log-dialog-con {
      margin-top:10px;
    }
    .log-dialog-con-header {
      list-style: none;
      padding: 0;
      margin:0;
      li {
        width: 50%;
        float: left;
        margin: 5px 0;
      }
    }
    .log-dialog-con-body {
      margin-top: 10px;;
    }
  }
  
</style>

