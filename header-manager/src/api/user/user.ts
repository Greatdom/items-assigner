import request from '@/utils/request'

// 登录
export function login(username: string, password: string) {
    // debugger
    return request({
        url: '/user/user/login',
        method: 'post',
        data: {
            username,//账号/手机/邮箱
            password //密码/验证码
        }
    })
}

// 获取用户信息
export function me() {
    return request({
        url: '/user/user/me',
        method: 'get',
    })
}

// 登出
export function logout() {
    //debugger
    return request({
        url: '/user/user/logout',
        method: 'post'
    })
}

export function get(id: number){
    return request({
        url: '/user/user/get',
        method: 'get',
        params: { id }
    })
}

export function selectAll(){
    return request({
        url: '/user/user/list',
        method: 'get'
    })
}

export function update(user: any){
    return request({
        url: '/user/user/update',
        method: 'put',
        data: user
    })
}

export function add(user: any){
    return request({
        url: '/user/user/add',
        method: 'post',
        data: user
    })
}
export function remove(id: number){
    return request({
        url: '/user/user/remove',
        method: 'delete',
        params: { id }
    })
}

