import request from '@/utils/request'


export function doAssign(roleId: number,permissionIds: number[]){
    return request({
        url: '/user/user/doAssign',
        method: 'post',
        params: { roleId,permissionIds }
    })
}

export function SelectAll(){
    return request({
        url: '/user/user/all',
        method: 'get'
    })
}

export function SelectById(id: number){
    return request({
        url: '/user/user/one',
        method: 'get',
        params: { id }
    })
}

export function add(permissions: any){
    return request({
        url: '/user/user/add',
        method: 'post',
        data: permissions
    })
}

