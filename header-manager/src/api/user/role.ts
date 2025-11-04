import request from '@/utils/request'

export function list(){
    return request({
        url: '/user/role/list',
        method: 'get'
    })
}

export function add(role: any){
    return request({
        url: '/user/role/add',
        method: 'post',
        data: role
    })
}

export function getByUser(userId: number){
    return request({
        url: '/user/role/getByUser',
        method: 'get',
        params: { userId }
    })
}

export function assignRole(userId: number,roleIds: number[]){
    return request({
        url: '/user/role/assignRole',
        method: 'post',
        params: { userId,roleIds }
    })
}
