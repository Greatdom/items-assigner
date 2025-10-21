import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import type { Permission, Menu, UserInfo } from '@/types/auth';

// 静态路由
const staticRoutes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: { title: '登录' }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes: staticRoutes
});

// 动态导入组件
const modules = import.meta.glob('@/views/**/*.vue');

// 将菜单转换为路由
export function menuToRoute(menu: Menu): any {
    const route: any = {
        path: menu.path,
        name: menu.name,
        meta: {
            title: menu.name,
            requiresAuth: true,
            ...menu.meta
        }
    };

    if (menu.component && modules[`/src/views/${menu.component}.vue`]) {
        route.component = modules[`/src/views/${menu.component}.vue`];
    } else {
        route.component = () => import('@/views/NotFound.vue');
    }

    if (menu.children && menu.children.length > 0) {
        route.children = menu.children.map(menuToRoute);
    }

    return route;
}

// 添加动态路由
export function addDynamicRoutes(menus: Menu[]) {
    const authStore = useAuthStore();
    const dynamicRoutes = menus.map(menuToRoute);

    dynamicRoutes.forEach(route => {
        router.addRoute(route);
    });

    authStore.setDynamicRoutes(dynamicRoutes);

    // 添加404页面
    router.addRoute({
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/views/NotFound.vue')
    });
}

// 路由守卫
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();

    if (to.meta.requiresAuth && !authStore.userInfo) {
        next('/login');
    } else {
        next();
    }
});

export default router;