import {createRouter, createWebHistory} from 'vue-router'
import Layout from '@/views/layout/Layout.vue'


/**
 * hidden: true                   若设置为true，该路由将不会在侧边栏中显示（默认值为false）
 * alwaysShow: true               若设置为true，无论子路由数量多少，都会始终显示父菜单
 *                                若不设置此属性，仅当子路由数量超过1个时，才会以嵌套模式显示父菜单；
 *                                若子路由数量为1个，则不会显示父菜单，直接显示子菜单
 * redirect: noredirect           若设置为"redirect:noredirect"，则在面包屑中不会触发重定向（无跳转箭头）
 *                                若设置为具体路径（如"/dashboard"），则点击父菜单时会重定向到该路径
 * name:'router-name'             路由的唯一名称，此名称会被<keep-alive>组件使用（必须设置！！！）
 *                                （注：需与对应页面组件的name属性保持一致，否则缓存失效）
 * meta : {
 title: 'title'               用于在侧边栏子菜单和面包屑中显示的文本（建议设置）
 icon: 'svg-name'             用于在侧边栏中显示的图标名称（需与项目中icons/svg目录下的svg文件名对应）
 }
 **/
export const constantRoutes = [
    {path: '/login', component: () => import('@/views/login/index.vue'), hidden: true },
    {path: '/404', component: () => import('@/views/404.vue'), hidden: true },

    // 首页
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        name: 'Dashboard',
        children: [{
            path: 'dashboard',
            component: () => import('@/views/dashboard/index.vue'),
            meta: { title: '计划经济分配机', icon: 'dashboard' }
        }]
    }
    ]

const router = createRouter({
    history: createWebHistory(), // History 模式（替代 Vue 3 的 mode: 'history'）
    scrollBehavior: () => ({ y: 0 }), // 路由切换时滚动到顶部
    routes: constantRoutes // 注入常量路由
})

export default router