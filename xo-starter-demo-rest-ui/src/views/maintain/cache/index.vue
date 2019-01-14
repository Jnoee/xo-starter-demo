<template>
  <div id="cache" style="padding:20px">
    <div class="cache-header">
      <el-button type="primary" size="mini" @click="delClassCacheData()">清理实体缓存</el-button>
      <el-button type="primary" size="mini" @click="delEntityCacheData()">清理所有实体缓存</el-button>
      <el-button type="primary" size="mini" @click="delCollectionCacheData()">清理所有集合缓存</el-button>
      <el-button type="primary" size="mini" @click="delQueryCacheData()">清理所有查询缓存</el-button>
      <el-button type="primary" size="mini" @click="delCacheAllData()">清理所有缓存</el-button>
    </div>
    <div class="cache-body">
      <el-table
        ref="multipleTable"
        :data="cacheList"
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
import { 
  getCacheList, 
  delCacheAll, 
  delClassCache, 
  delCollectionCache, 
  delEntityCache, 
  delQueryCache 
} from '@/bin/api/caches'

export default {
  name:'Indexes',
  data() {
    return {
      cacheList: [],                // 缓存列表
      multipleSelection: [],        // 复选框选中数据
      clearData: [],                // 需要清理的实体缓存
      tips1:['您确定要清理选中的实体缓存吗？', '清理实体缓存成功。', '清理实体缓存失败。'],
      tips2:['您确定要清理所有实体缓存吗？', '清理所有实体缓存成功。', '清理所有实体缓存失败。'],
      tips3:['您确定要清理所有集合缓存吗？', '清理所有集合缓存成功。', '清理所有集合缓存成功。'],
      tips4:['您确定要清理所有查询缓存吗？', '清理所有查询缓存成功。', '清理所有查询缓存成功。'],
      tips5:['您确定要清理所有缓存吗？', '清理所有缓存成功。', '清理所有缓存成功。'],
    }
  },
  mounted() {
    this.initData()
  },
  methods: {
    
    // 初始化列表数据
    initData() {
      getCacheList().then(res => {
        let data = res.data
        data.map((item,index) => {
          let obj = {}
          obj.name = item
          this.cacheList.push(obj)
        })
      })
    },

    // 复选框选择数据
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 清理指定实体缓存
    delClassCacheData() {
      if(!this.multipleSelection.length) {
        this.$message({
          message: '请选择要操作的记录!',
          type: 'info',
          center:true,
          duration: 5 * 1000
        });
      } else {
        this.multipleSelection.map((item,index) => {
          this.clearData.push(item.name)
        })
        this.clearCacheData(delClassCache, this.tips1, this.clearData)
      }
    },

    // 清理所有实体缓存
    delEntityCacheData() {
      this.clearCacheData(delEntityCache, this.tips2)
    },

    // 清理所有集合缓存
    delCollectionCacheData() {
      this.clearCacheData(delCollectionCache, this.tips3)
    },

    // 清理所有查询缓存
    delQueryCacheData() {
      this.clearCacheData(delQueryCache, this.tips4)
    },

    // 清理所有缓存
    delCacheAllData() {
      this.clearCacheData(delCacheAll, this.tips5)
    },

    // 清理缓存方法
    clearCacheData(func, tips, data) {
      this.$confirm(tips[0], '确认提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        func(data).then(res => {
          this.$message({
            message: tips[1],
            type: 'success',
            center:true,
          })
          this.$refs.multipleTable.clearSelection();
        }).catch(err => {
          this.$message({
            message: tip[2],
            type: 'error',
            center:true
          })
        })
      }).catch(err => {

      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .cache-body {
    margin-top: 20px;
  }
</style>

