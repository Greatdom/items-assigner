export interface UserLoginForm {
    username: string;
    password: string;
}
export interface CurrentUser {
    id: number;
    username: string;
    nickName: string;
    salt: string;
    permissionValueList: string[];
    roles: string[];
}