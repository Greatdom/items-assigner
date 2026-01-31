import request from '@/utils/request'
import type {UserLoginForm} from "@/types/user.ts";


// 登录
export function login(UserLoginForm: UserLoginForm) {
    // debugger
    return request({
        url: '/user/auth/login',
        method: 'post',
        data: UserLoginForm
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
export function phoneCode(phone:String){
    return request({
        url:`/user/auth/phoneCode/${phone}`,
        method:"get",
        params:{phone}
    })
}
export function emailCode(email:String){
    return request({
        url:`/user/auth/emailCode/${email}`,
        method:"get",
        params:{email}
    })
}