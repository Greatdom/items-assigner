import type {CurrentUser} from "@/types/user.ts";

export const data:CurrentUser = {
    "id": 1,
    "username": "wddyxd",
    "nickName": "wddyxd",
    "salt": "",
    "roles": [
        "ROLE_ADMIN"
    ],
    "permissionValueList": [
        "user.add",
        "user.update",
        "user.list",
        "user.remove",
        "role.add",
        "role.list",
        "role.update",
        "role.delete",
        "permissions.add",
        "permissions.update",
        "permissions.list",
        "permissions.remove"
    ]
}

