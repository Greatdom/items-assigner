import request from "@/utils/request.ts";
import type {SearchDTO} from "@/types/user.ts";

export function feed(SearchDTO:SearchDTO) {
    // debugger
    return request({
        url: '/product/product/feed',
        method: 'get',
        params: SearchDTO
    })
}

export function visit(id:number) {
    //debugger
    return request({
        url: `/product/product/visit/${id}`,
        method: 'get',
        params:{id}
    })
}
