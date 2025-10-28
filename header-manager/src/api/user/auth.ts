import request from '@/utils/request'

// 登录
export function login(username: string, password: string) {
    // debugger
    return request({
        url: '/user/auth/login',
        method: 'post',
        data: {
            username,//账号/手机/邮箱
            password //密码/验证码
        }
    })
}

// 登出
export function logout() {
    //debugger
    return request({
        url: '/user/auth/logout',
        method: 'post'
    })
}