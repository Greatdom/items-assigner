import axios from 'axios'
import { ElMessage } from 'element-plus'
// import store from '../store'
// import {getToken} from '@/utils/auth'
//
// 创建axios实例
const service = axios.create({
    // baseURL: process.env.BASE_API, // api 的 base_url
    //TODO 可以写配置文件
    baseURL: 'http://localhost:10010',
    timeout: 20000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
    config => {
        // if (store.getters.token) {
        //     config.headers['token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
        // }
        config.headers['token'] = sessionStorage.getItem('token')
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
        /**
         * code为非20000是抛错 可结合自己业务进行修改
         */
        const res:any = response.data
        const msg:string = res.msg

        // debugger
        if (res.code !== 200) {
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
