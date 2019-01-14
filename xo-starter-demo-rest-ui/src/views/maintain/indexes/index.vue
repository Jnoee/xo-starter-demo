<template>
  <div id="indexes" style="padding:20px;">
    <div class="indexes-header">
      <el-button type="primary" size="mini" @click="reBuild">重建全文索引</el-button>
    </div>
    <div class="indexes-body">
      <el-table
        ref="multipleTable"
        :data="indexList"
        tooltip-effect="dark"
        style="width: 100%"
        border
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="name"
          label="实体类名"
          >
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getIndexList, rebuildIndex } from '@/bin/api/indexs'

export default {
  name:'Indexes',
  data() {
    return {
      indexList: [],                // 列表数据
      multipleSelection: [],        // 复选框选中数据
      rebuildData: []               // 重建索引数据
    }
  },
  mounted() {
    this.initData()
  },
  methods: {

    // 初始化获取数据
    initData() {
      getIndexList().then(res => {
        let data = res.data
        data.map((item,index) => {
          let obj = {}
          obj.name = item
          this.indexList.push(obj)
        })
      }).catch(err => {
        console.log(err)
      })
    },

    // 复选框选择数据
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 重建索引
    reBuild() {
      if(!this.multipleSelection.length) {
        this.$message({
          message: '请选择要操作的记录!',
          type: 'info',
          center:true
        });
      } else {
        this.multipleSelection.map((item,index) => {
          this.rebuildData.push(item.name)
        })

        this.$confirm('您确定要重建这些全文索引吗?', '确认提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          rebuildIndex(this.rebuildData).then(res => {
            this.$message({
              message: '重建全文索引成功',
              type: 'success',
              center:true,
            })
            this.$refs.multipleTable.clearSelection();
          }).catch(err => {
            this.$message({
              message: '重建全文索引失败',
              type: 'error',
              center:true
            })
          })
        }).catch(err => {

        })
      }
    },
  }
}
</script>

<style lang="scss" scoped>
  .indexes-body {
    margin-top: 20px;
  }
</style>




