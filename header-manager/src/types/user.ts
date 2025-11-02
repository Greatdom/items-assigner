export interface UserLoginForm {
    id:number;
    username: string;
    password: string;
    phone: string;
    phoneCode: string;
    email: string;
    emailCode: string;
    loginType: string;

}
export interface CurrentUser {
    id: number;
    username: string;
    nickName: string;
    salt: string;
    permissionValueList: string[];
    roles: string[];
}