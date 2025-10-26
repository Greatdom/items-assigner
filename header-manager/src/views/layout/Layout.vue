<script setup lang="ts">
import Navbar from "@/views/layout/components/navbar.vue";
import Sidebar from "@/views/layout/components/sidebar.vue";
import AppMain from "@/views/layout/components/app-main.vue";
</script>

<template>
  <div class="app-wrapper">
    <div class="drawer-bg">
      <sidebar class="sidebar-container"></sidebar>
      <div class="main-container">
        <navbar/>
        <app-main/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.app-wrapper {
  position: relative;
  height: 100vh;
  width: 100%;
  overflow: hidden;
}

.drawer-bg {
  display: flex;
  height: 100%;
  width: 100%;
}

.main-container {
  flex: 1;
  margin-left: 200px; /* 与侧边栏宽度保持一致 */
}
</style>
