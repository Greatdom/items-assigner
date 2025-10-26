import {createRouter, createWebHistory} from 'vue-router'
import Layout from '@/views/layout/Layout.vue'
import {useUserStore} from "@/store/modules/user.ts";


export const constantRoutes = [
    {path: '/login', component: () => import('@/views/login/index.vue')},
    {path: '/404', component: () => import('@/views/404.vue')},
    {path: '/403', component: () => import('@/views/403.vue')},

    // 首页
    {
        path: '/',
        component: Layout,
        redirect: '/Home',
        children: [
            {
                path: '/Home',
                component: () => import('@/views/home/index.vue'),
                meta: {
                    title: '首页',
                    icon: 'el-icon-s-home',
                    requiresAuth: false
                }
            },
            {
                path: '/components/user/update/:id',
                component: () => import('@/views/components/user/form.vue'),
                meta: {
                    title: '更新用户',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        if (user.permissionValueList?.includes('user.updateth')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        return user.id === route.params.id
                    }
                }
            },
            {
                path:'/components/user/list',
                component: () => import('@/views/components/user/list.vue'),
                meta: {
                    title: '用户列表',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.list 权限直接通过
                        return user.permissionValueList?.includes('user.list');
                    }
                }
            },
            {
                path:'/components/user/add',
                component: () => import('@/views/components/user/form.vue'),
                meta:{
                    title: '添加用户',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    hasPermission: (user:any, route:any) => {
                        return user.permissionValueList?.includes('user.add');
                    }
                }
            },
            {
                path:'/components/permissions/list',
                component: () => import('@/views/components/permissions/list.vue'),
                meta: {
                    title: '权限列表',
                    icon: 'el-icon-s-custom',
                    requiresAuth: false
                }
            }
        ]
    }
    ]

const router = createRouter({
    history: createWebHistory(), // History 模式（替代 Vue 3 的 mode: 'history'）
    // scrollBehavior: () => ({ y: 0 }), // 路由切换时滚动到顶部
    routes: constantRoutes // 注入常量路由
})

// 路由守卫：每次路由跳转前验证权限
router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore()

    const token:string = userStore.token
    const isLogin:boolean = (token!=null&&token!='')

    // 1. 未登录且需要权限的页面，跳转登录
    if (to.meta.requiresAuth && !isLogin) {
        return next('/login')
    }

    // 2. 已登录但未加载用户信息（如刷新页面），先加载个人信息
    if (to.meta.requiresAuth && isLogin && userStore.currentUser.id<=0) {
        try {
            await userStore.getCurrentUser(); // 从接口加载用户信息（含权限）
        } catch (error) {
            // 如果加载用户信息失败，可能是token失效，需要重新登录
            await userStore.logout();
            return next('/login');
        }
    }

    // 3. 权限判断：如果路由有自定义权限函数，执行判断
    if (to.meta.requiresAuth && to.meta.hasPermission) {
        // const hasAccess = to.meta.hasPermission(userStore.currentUser, to)
        //TODO 调试发现没有调用此方法或没有识别到userStore的currentUser
        const hasAccess = (to.meta.hasPermission as (user: any, route: any) => boolean)(userStore.currentUser, to)
        if (!hasAccess) {
            return next('/403') // 无权限跳转403页面
        }
    }

    next()
})

export default router