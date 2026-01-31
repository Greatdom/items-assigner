import request from "@/utils/request.ts";
import type {orderParams} from "@/types/order.ts";
import type {SearchDTO} from "@/types/user.ts";



export function add(orderParams: orderParams){
    return request({
        url: '/order/orderMain/add',
        method: 'post',
        data: orderParams
    })
}
export function listUser(SearchDTO:SearchDTO){
    return request({
        url: '/order/orderMain/list/user',
        method: 'get',
        params:SearchDTO
    })
}
export function pay(id:number){
    return request({
        url: `/order/orderStatusLog/pay/${id}`,
        method: 'post',
        params:{id}
    })
}
export function rollback(id:number){
    return request({
        url: `/order/orderStatusLog/rollback/${id}`,
        method: 'post',
        params:{id}
    })
}
export function cancel(id:number){
    return request({
        url: `/order/orderStatusLog/cancel/${id}`,
        method: 'post',
        params:{id}
    })
}