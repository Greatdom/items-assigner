import request from '@/utils/request'
import type {SearchDTO} from "@/types/user.ts";
import type {RoleAssignDTO} from "@/types/role.ts";

export function list(SearchDTO:SearchDTO){
    return request({
        url: '/user/role/list',
        method: 'get',
        data:SearchDTO
    })
}

export function add(role: any){
    return request({
        url: '/user/role/add',
        method: 'post',
        data: role
    })
}


export function assign(RoleAssignDTO : RoleAssignDTO){
    return request({
        url: '/user/role/assign',
        method: 'post',
        data: RoleAssignDTO
    })
}
