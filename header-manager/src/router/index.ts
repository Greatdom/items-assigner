import {createRouter, createWebHistory} from 'vue-router'
import Layout from '@/views/layout/Layout.vue'
import {useUserStore} from "@/store/modules/user.ts";


export const constantRoutes = [
    {path: '/login', component: () => import('@/views/login/Layout.vue')},
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
                component: () => import('@/views/components/person/index.vue'),
                meta: {
                    title: '首页',
                    icon: 'el-icon-s-home',
                    requiresAuth: false
                }
            },
            {
                path: '/user/update/:id',
                component: () => import('@/views/components/person/form.vue'),
                meta: {
                    title: '更新用户头像',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        // if (user.permissionValueList?.includes('user.update')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        return user.id == route.params.id
                    }
                }
            },
            {
                path:'/user/list',
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
                path:'/role/list',
                component: () => import('@/views/components/role/list.vue'),
                meta: {
                    title: '角色列表',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.list 权限直接通过
                        return user.permissionValueList?.includes('role.list');
                    }
                }
            },
            {
                path: '/role/detail/:id',
                component: () => import('@/views/components/role/form.vue'),
                meta: {
                    title: '查看具体角色',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        if (user.permissionValueList?.includes('role.list')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        // return user.id === route.params.id
                    }
                }
            },
            {
                //实际上推送商品是不需要登录也能访问的
                path:'/product/list',
                component: () => import('@/views/components/product/list.vue'),
                meta: {
                    title: '推送商品列表',
                    icon: 'el-icon-s-custom',
                    requiresAuth: false

                }
            },
            {
                path: '/product/visit/:id',
                component: () => import('@/views/components/product/visit.vue'),
                meta: {
                    title: '查看具体商品和下单',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        if (user.permissionValueList?.includes('product.list')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        // return user.id === route.params.id
                    }
                }
            },
            {
                path:'/coupon/list',
                component: () => import('@/views/components/coupon/list.vue'),
                meta: {
                    title: '查看优惠券列表,抢优惠券',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        if (user.permissionValueList?.includes('product.list')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        // return user.id === route.params.id
                    }
                }
            },
            {
                path:'/file/operate',
                component: () => import('@/views/components/file/video-operator.vue'),
                meta: {
                    title: '大文件操作',
                    icon: 'el-icon-s-custom',
                    requiresAuth: false

                }
            },
            {
                path: '/order/operate/:id',
                component: () => import('@/views/components/order/form.vue'),
                meta: {
                    title: '订单操作',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    // 权限判断函数：返回是否有权限访问
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.update 权限直接通过
                        if (user.permissionValueList?.includes('orderStatusLog.add')) return true
                        // 2. 无权限时判断用户ID是否与路由参数ID一致
                        // return user.id === route.params.id
                    }
                }
            },
            {
                path:'/order/list',
                component: () => import('@/views/components/order/list.vue'),
                meta: {
                    title: '用户的订单列表',
                    icon: 'el-icon-s-custom',
                    requiresAuth: true,
                    hasPermission: (user:any, route:any) => {
                        // 1. 有 user.list 权限直接通过
                        return user.permissionValueList?.includes('order.list');
                    }
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
    if (isLogin && +userStore.currentUser.id <= 0) {
        try {
            await userStore.getCurrentUser(); // 从接口加载用户信息（含权限）
        } catch (error) {
            // 如果加载用户信息失败，可能是token失效，需要重新登录
            await userStore.HandleLogout();
            return next('/login');
        }
    }

    // 3. 权限判断：如果路由有自定义权限函数，执行判断
    if (to.meta.requiresAuth && to.meta.hasPermission) {
        const hasAccess = (to.meta.hasPermission as (user: any, route: any) => boolean)(userStore.currentUser, to)
        if (!hasAccess) {
            return next('/403') // 无权限跳转403页面
        }
    }

    next()
})

export default router