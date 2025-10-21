<template>
  <div class="home">
    <h1>首页</h1>
    <p>欢迎来到首页</p>

    <!-- 权限按钮示例 -->
    <button v-permission="'user:add'">添加用户</button>
    <button v-permission="'user:delete'">删除用户</button>
    <button v-permission="'system:config'">系统配置</button>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();
</script>

<style scoped>

</style>
<script lang="ts">
export default {
  name: 'Home'
}
</script>