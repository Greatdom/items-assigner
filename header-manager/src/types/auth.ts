export interface Permission {
    id: string;
    code: string;
    name: string;
    type: 'menu' | 'button' | 'api';
}

export interface Menu {
    id: string;
    name: string;
    path: string;
    component?: string;
    icon?: string;
    children?: Menu[];
    permissions?: string[];
    meta?: {
        title: string;
        requiresAuth: boolean;
    };
}

export interface UserInfo {
    id: string;
    username: string;
    permissions: string[];
}