import request from "@/utils/request.ts";
import type {UpdateAvatarParams} from "@/types/file.ts";

export function updateAvatar(params: UpdateAvatarParams) {
    // 创建FormData对象，用于传输文件和表单数据
    const formData = new FormData();
    formData.append('file', params.file); // 文件对象
    formData.append('userId', params.userId.toString()); // 转为字符串，避免类型问题
    formData.append('oldAvatar', params.oldAvatar);

    return request({
        url: '/file/update/avatar',
        method: 'post',
        data: formData,
        //显式设置Content-Type为multipart/form-data,axios通常会自动处理
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}
//使用示例
// const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
// if (fileInput.files?.[0]) {
//     updateAvatar({
//         file: fileInput.files[0],
//         userId: 123,
//         oldAvatar: '/uploads/old-avatar.jpg'
//     }).then(res => {
//         console.log('头像更新成功', res);
//     }).catch(err => {
//         console.error('头像更新失败', err);
//     });
// }