export interface ProductFeedDTO {
    pageNum?:number;
    pageSize?:number;
    search?:string;
    categoryId?:string;
    sortColumn:string;
    sortOrder:string
}