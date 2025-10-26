<script setup lang="ts">

</script>

<template>
  <div class="sidebar">
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
          :show-timeout="200"
          :default-active="$route.path"
          mode="vertical"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
      >
      </el-menu>
    </el-scrollbar>
  </div>

</template>

<style scoped>
.sidebar {
  background-color: #2c3e50;
  width: 200px;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
}

.el-menu {
  border-right: none;
  height: 100%;
}

.el-menu-item {
  padding-left: 25px !important;
  height: 50px;
  line-height: 50px;
}
</style>
