import request from '@/utils/request'
import type {SearchDTO} from "@/types/user.ts";


// 获取用户信息
export function me() {
    return request({
        url: '/user/user/me',
        method: 'get',
    })
}

export function list(SearchDTO:SearchDTO){
    return request({
        url: '/user/user/list',
        method: 'get',
        params: SearchDTO
    })
}

