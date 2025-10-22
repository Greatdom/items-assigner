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

export function getByUser(userId: string){
    return request({
        url: '/user/role/getByUser',
        method: 'get',
        params: { userId }
    })
}

export function assignRole(userId: string,roleIds: string[]){
    return request({
        url: '/user/role/assignRole',
        method: 'post',
        params: { userId,roleIds }
    })
}
