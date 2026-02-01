export interface UserLoginForm {
    id:string;
    username: string;
    password: string;
    phone: string;
    phoneCode: string;
    email: string;
    emailCode: string;
    loginType: string;

}
export interface CurrentUser {
    id: string;
    username: string;
    nickName: string;
    avatar: string;
    permissionValueList: string[];
    roles: string[];
}

export interface SearchDTO {
    pageNum?:number;
    pageSize?:number;
    search?:String;
}