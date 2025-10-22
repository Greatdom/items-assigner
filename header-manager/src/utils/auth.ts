export function getToken() {
    const token:string|null = sessionStorage.getItem('token')
    return token || ''
}

export function setToken(token:string) {
    // return Cookies.set(TokenKey, token)
    sessionStorage.setItem('token', token)
}

export function removeToken() {
    sessionStorage.removeItem('token')
}

