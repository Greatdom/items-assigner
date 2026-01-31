export interface ProductFeedDTO {
    pageNum?:number;
    pageSize?:number;
    search?:String;
    categoryId?:number;
    sortColumn:String;
    sortOrder:String
}