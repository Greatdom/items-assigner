import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, removeToken } from "@/utils/auth.ts";
import { CONFIG } from "@/config";

 // 处理原始JSON字符串，将其中的大整数转为字符串（关键：处理字符串而非解析后的对象）
function processBigIntInJson(jsonStr: string): any {
    if (!jsonStr) return jsonStr;

    // 正则匹配 JSON 中所有数字值（冒号后、逗号/花括号/方括号前的数字）
    // 匹配规则：": 数字" 格式，且数字超过16位（安全整数临界值）
    const bigIntRegex = /:(\s*)(\d{17,})(?=\s*[,}\]])/g;

    const processedStr = jsonStr.replace(bigIntRegex, ':$1"$2"');

    try {
        return JSON.parse(processedStr);
    } catch (e) {
        console.error('处理大整数失败，使用默认解析:', e);
        return JSON.parse(jsonStr);
    }
}




const service = axios.create({
    baseURL: CONFIG.baseURL,
    timeout: CONFIG.timeout,
    // 在transformResponse中处理原始JSON字符串
    transformResponse: [
        function (data) {
            return  processBigIntInJson(data);
        }
    ]
})

// request拦截器
service.interceptors.request.use(
    config => {
        config.headers['token'] = getToken()
        return config
    },
    error => {
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response 拦截器
service.interceptors.response.use(
    response => {
        // 此时 response.data 已经是处理后的结果（大整数已转字符串）
        const res: any = response.data
        const msg: string = res.msg

        if (res.code !== 200) {
            if (res.code === 401) {
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
            // 无需再调用convertBigIntToString，已经在transformResponse处理完了
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