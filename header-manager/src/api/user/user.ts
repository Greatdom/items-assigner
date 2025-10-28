import request from '@/utils/request'


// 获取用户信息
export function me() {
    return request({
        url: '/user/user/me',
        method: 'get',
    })
}



export function get(id: number){
    return request({
        url: '/user/user/get',
        method: 'get',
        params: { id }
    })
}

export function selectAll(pageNum: number, pageSize: number, search: string){
    return request({
        url: '/user/user/list',
        method: 'get',
        params: { pageNum, pageSize, search }
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
export function remove(id: number): Promise<any>{
    return request({
        url: '/user/user/remove',
        method: 'delete',
        params: { id }
    })
}

