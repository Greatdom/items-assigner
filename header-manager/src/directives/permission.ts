import type { Directive } from 'vue';
import { useAuthStore } from '@/stores/auth';

export const permission: Directive = {
    mounted(el, binding) {
        const authStore = useAuthStore();
        const { value } = binding;

        if (value && !authStore.hasPermission(value)) {
            el.parentNode?.removeChild(el);
        }
    }
};