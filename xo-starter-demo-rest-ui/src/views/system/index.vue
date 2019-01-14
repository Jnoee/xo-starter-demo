<template>
  <div class="system-container">
    <h3>首页</h3>  
    <span v-permission="['log']">权限控制显示</span>
  </div>
</template>

<script>
let id = 1000;
export default {
  name: 'System',
  computed: {
    
  },
  data() {
    return {
      
    }
  },
  methods: {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.system {
  &-container {
    padding:20px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
