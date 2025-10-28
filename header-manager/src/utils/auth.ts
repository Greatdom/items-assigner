export function getToken() {
    const token:string|null = localStorage.getItem('token')
    return token || ''
}

export function setToken(token:string) {
    // return Cookies.set(TokenKey, token)
    localStorage.setItem('token', token)
}

export function removeToken() {
    localStorage.removeItem('token')
}

