import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { Permission, Menu, UserInfo } from '@/types/auth';

export const useAuthStore = defineStore('auth', () => {
    // 状态
    const permissions = ref<Permission[]>([]);
    const menus = ref<Menu[]>([]);
    const userInfo = ref<UserInfo | null>(null);
    const dynamicRoutes = ref<any[]>([]);

    // Getter
    const hasPermission = computed(() => (permissionCode: string) => {
        if (!userInfo.value) return false;
        return userInfo.value.permissions.includes(permissionCode);
    });

    const permissionCodes = computed(() => {
        return userInfo.value?.permissions || [];
    });

    // Actions
    const setPermissions = (newPermissions: Permission[]) => {
        permissions.value = newPermissions;
    };

    const setMenus = (newMenus: Menu[]) => {
        menus.value = newMenus;
    };

    const setUserInfo = (info: UserInfo) => {
        userInfo.value = info;
    };

    const setDynamicRoutes = (routes: any[]) => {
        dynamicRoutes.value = routes;
    };

    // 检查菜单权限
    const hasMenuPermission = (menu: Menu): boolean => {
        if (!menu.permissions || menu.permissions.length === 0) return true;
        return menu.permissions.some(permission => hasPermission.value(permission));
    };

    // 过滤有权限的菜单
    const filterAuthorizedMenus = (menuList: Menu[]): Menu[] => {
        return menuList.filter(menu => {
            if (menu.children && menu.children.length > 0) {
                menu.children = filterAuthorizedMenus(menu.children);
                return menu.children.length > 0 || hasMenuPermission(menu);
            }
            return hasMenuPermission(menu);
        });
    };

    const authorizedMenus = computed(() => {
        return filterAuthorizedMenus(menus.value);
    });

    return {
        permissions,
        menus,
        userInfo,
        dynamicRoutes,
        hasPermission,
        permissionCodes,
        authorizedMenus,
        setPermissions,
        setMenus,
        setUserInfo,
        setDynamicRoutes,
        filterAuthorizedMenus
    };
});