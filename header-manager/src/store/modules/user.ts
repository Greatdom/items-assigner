import { me } from '@/api/user/user.ts'
import {login,logout} from '@/api/user/auth.ts'
import { getToken, setToken, removeToken } from '@/utils/auth'
import {defineStore} from "pinia";
import type {CurrentUser, UserLoginForm} from "@/types/user.ts";
import {ref} from "vue";
import {data} from "@/data/test.ts";
import {useRouter} from "vue-router";

export const useUserStore = defineStore('user', ()=>{

    const router = useRouter()

    let token = ref<string>(getToken() || ''); // 初始值取缓存，无则为空字符串

    let currentUser = ref<CurrentUser>({
        id: -1,
        username: '',
        nickName: '',
        salt: '',
        permissionValueList: [],
        roles: []
    });

    function SET_TOKEN(TOKEN: string) {
        token.value = TOKEN; // ref 变量需通过 .value 访问/修改
        setToken(TOKEN);
    }
    function SET_CURRENT_USER(CURRENT_USER: CurrentUser) {
        currentUser.value = CURRENT_USER;
    }


    function Login(userLoginForm:UserLoginForm):Promise<any>{
        const username = userLoginForm.username.trim()

        return new Promise((resolve, reject) => {
            login(username, userLoginForm.password).then(response => {
                // debugger
                const data = response.data
                console.log("STORE:"+data)
                setToken(data)
                SET_TOKEN(data)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    }
            // 获取用户信息
    function getCurrentUser():Promise<any>{
        return new Promise((resolve, reject) => {
            me().then(response => {
                // debugger
                const data = response.data
                SET_CURRENT_USER(data)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    }


    function HandleLogout():Promise<any>{
        return new Promise((resolve, reject) => {
            logout().then(response => {
                // debugger
                const data = response.data
                SET_TOKEN('')
                removeToken()
                resolve(response)
            }).catch(error => {
                reject(error)
            }).then(() => {

                router.push('/login').then(() => {
                    //刷新相关参数不然可能会有bug
                    location.reload();
                });
            })
        })
    }


    return{
        token,
        Login,
        currentUser,
        getCurrentUser,
        HandleLogout
    };
})

