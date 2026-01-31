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

export function detail(id:number) {
    //debugger
    return request({
        url: `/user/role/detail/${id}`,
        method: 'get',
        params:{id}
    })
}
