<template>
  <div class="login">
    <h1>登录</h1>
    <button @click="handleLogin">模拟登录</button>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const handleLogin = async () => {
  // 模拟从后端获取数据
  const permissionsData = await import('@/data/permissions.json');
  const menusData = await import('@/data/menus.json');

  // 设置用户信息和权限
  authStore.setUserInfo({
    id: '1',
    username: 'admin',
    permissions: ['user:add', 'user:edit', 'system:view']
  });

  authStore.setPermissions(permissionsData.default);
  authStore.setMenus(menusData.default);

  // 添加动态路由
  const { addDynamicRoutes } = await import('@/router');
  addDynamicRoutes(authStore.authorizedMenus);

  router.push('/');
};
</script>

<style scoped>

</style>
<script lang="ts">
export default {
  name: 'Login'
}
</script>