<script setup lang="ts">
import Navbar from "@/views/layout/components/navbar.vue";
import Sidebar from "@/views/layout/components/sidebar.vue";
import AppMain from "@/views/layout/components/app-main.vue";
</script>

<template>
  <div class="app-container">
    <!-- 侧边栏 -->
    <Sidebar />

    <!-- 主内容区 (包含导航栏和页面内容) -->
    <div class="main-content">
      <Navbar/>
      <AppMain/>
    </div>
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  min-height: 100vh;

}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}
</style>