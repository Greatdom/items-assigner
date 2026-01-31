import request from '@/utils/request'
import type {SearchDTO} from "@/types/user.ts";


// 获取用户信息
export function me() {
    return request({
        url: '/user/user/me',
        method: 'get',
    })
}



export function detail(id: number){
    return request({
        url: `/user/user/detail/${id}`,
        method: 'get',
        params: { id }
    })
}

export function list(SearchDTO:SearchDTO){
    return request({
        url: '/user/user/list',
        method: 'get',
        params: SearchDTO
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
