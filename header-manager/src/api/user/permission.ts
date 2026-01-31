import request from '@/utils/request'
import type {SearchDTO} from "@/types/user.ts";


export function assign(roleId: number,permissionIds: number[]){
    return request({
        url: '/user/permission/assign',
        method: 'post',
        params: { roleId,permissionIds }
    })
}

export function list(SearchDTO:SearchDTO){
    return request({
        url: '/user/permission/list',
        method: 'get',
        data: SearchDTO
    })
}


export function add(permissions: any){
    return request({
        url: '/user/permission/add',
        method: 'post',
        data: permissions
    })
}

