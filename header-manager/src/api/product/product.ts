import request from "@/utils/request.ts";
import type {ProductFeedDTO} from "@/types/product.ts";

export function feed(ProductFeedDTO:ProductFeedDTO) {
    // debugger
    return request({
        url: '/product/product/feed',
        method: 'get',
        params: ProductFeedDTO
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
