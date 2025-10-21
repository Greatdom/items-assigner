<template>
  <div id="app">
    <nav v-if="$route.path !== '/login'">
      <router-link to="/">首页</router-link>
      <template v-for="menu in authStore.authorizedMenus" :key="menu.id">
        <router-link :to="menu.path">{{ menu.name }}</router-link>
      </template>
    </nav>

    <main>
      <router-view />
    </main>
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
  name: 'App',
}
</script>