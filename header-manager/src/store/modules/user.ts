import { login, logout, me } from '@/api/user/user.ts'
import { getToken, setToken, removeToken } from '@/utils/auth'
import {defineStore} from "pinia";
import type {UserInfo} from "@/types/user.ts";
import {ref} from "vue";

// import { defineStore } from "pinia";
// import { computed, ref } from "vue";
//
// export const useCountStore = defineStore('count', () => {
//     const sum = ref(18);
//
//     const bignum = computed({
//         get() {
//             return sum.value * 10;
//         },
//         set(val) {
//             sum.value = val / 10;
//         }
//     });
//
//     return { sum, bignum };
// });

export const useUserStore = defineStore('user', ()=>{



    const token = ref<string>(getToken() || ''); // 初始值取缓存，无则为空字符串
    const name = ref<string>('');
    const avatar = ref<string>('');
    const buttons = ref<string[]>([]);
    const roles = ref<string[]>([]);
    function SET_TOKEN(TOKEN: string) {
        token.value = TOKEN; // ref 变量需通过 .value 访问/修改
        setToken(TOKEN);
    }
    function SET_NAME(NAME: string) {
        name.value = NAME;
    }
    function SET_AVATAR(AVATAR: string) {
        avatar.value = AVATAR;
    }
    function SET_BUTTONS(BUTTONS: string[]) {
        buttons.value = BUTTONS;
    }
    function SET_ROLES(ROLES: string[]) {
        roles.value = ROLES;
    }
    function Login(userInfo:UserInfo):Promise<any>{
        const username = userInfo.username.trim()

        return new Promise((resolve, reject) => {
            login(username, userInfo.password).then(response => {
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
    // function getInfo(){
    //     return new Promise((resolve, reject)=>{
    //         me().then(response => {
    //             const data = response.data
    //
    //         })
    //     })
    // }


    return{
        token,
        name,
        avatar,
        buttons,
        roles,
        SET_TOKEN,
        SET_NAME,
        SET_AVATAR,
        SET_BUTTONS,
        SET_ROLES,
        Login,
    };
})



//         // 获取用户信息
//         async getInfo({ commit, state }) {
//             return new Promise((resolve, reject) => {
//                 me(state.token).then(response => {
//                     // debugger
//                     const data = response.data
//                     if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
//                         commit('SET_ROLES', data.roles)
//                     } else {
//                         reject('getInfo: roles must be a non-null array !')
//                     }
//
//                     const buttonAuthList = []
//                     data.permissionValueList.forEach(button => {
//                         buttonAuthList.push(button)
//                     })
//
//                     commit('SET_NAME', data.name)
//                     commit('SET_AVATAR', data.avatar)
//                     commit('SET_BUTTONS', buttonAuthList)
//                     resolve(response)
//                 }).catch(error => {
//                     reject(error)
//                 })
//             })
//         },
//
//         LogOut({ commit, state }) {
//             return new Promise((resolve, reject) => {
//                 logout(state.token).then(() => {
//                     debugger
//                     commit('SET_TOKEN', '')// 清空前端vuex中存储的数据
//                     commit('SET_ROLES', [])// 清空前端vuex中存储的数据
//                     commit('SET_BUTTONS', [])
//                     removeToken()// 清空cookie
//                     resolve()
//                 }).catch(error => {
//                     reject(error)
//                 })
//             })
//         },
//
//         // 前端 登出
//         FedLogOut({ commit }) {
//             return new Promise(resolve => {
//                 debugger
//                 commit('SET_TOKEN', '')
//                 removeToken()
//                 resolve()
//             })
//         }
//     }
// }
//
// export default user


