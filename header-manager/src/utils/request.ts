import axios from 'axios'
import { ElMessage } from 'element-plus'
import {getToken, removeToken} from "@/utils/auth.ts";
import {useRouter} from "vue-router";
import {CONFIG} from "@/config";

// 创建axios实例
const service = axios.create({
    baseURL:CONFIG.baseURL,
    timeout:CONFIG.timeout
})

// request拦截器
service.interceptors.request.use(
    config => {
        // if (store.getters.token) {
        //     config.headers['token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
        // }
        config.headers['token'] = getToken()
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    }
)

// response 拦截器
service.interceptors.response.use(
    response => {

        const res:any = response.data
        const msg:string = res.msg

        // debugger
        if (res.code !== 200) {

            if(res.code === 403){
                removeToken()
                // 动态导入router并执行跳转
                import('@/router').then(routerModule => {
                    routerModule.default.push('/login')
                })
            }
            ElMessage({
                message: msg,
                type: 'error',
                duration: 5 * 1000
            })
            return Promise.reject(msg)
        } else {
            return response.data
        }
    },
    error => {
        console.log('err' + error) // for debug
        ElMessage({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
