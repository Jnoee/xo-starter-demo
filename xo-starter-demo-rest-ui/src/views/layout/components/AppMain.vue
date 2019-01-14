<template>
<section class="app-main">
  <transition name="fade-transform" mode="out-in">
    <router-view />
  </transition>
</section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {}
}
</script>

<style scoped>
.app-main {
  min-height: calc(100vh - 84px);
  padding-top: 84px;
  position: relative;
  overflow: hidden;
}
</style>
