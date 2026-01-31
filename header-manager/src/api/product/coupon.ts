import request from "@/utils/request.ts";
import type {SearchDTO} from "@/types/user.ts";



export function list(SearchDTO:SearchDTO){
    return request({
        url: '/product/coupon/list',
        method: 'get',
        params: SearchDTO
    })
}

export function add(id:number){
    return request({
        url: `/product/userCoupon/add/${id}`,
        method: 'post',
        params:{id}
    })
}
