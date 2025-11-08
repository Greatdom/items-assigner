// src/config/index.ts
interface Config {
    baseURL: string;
    timeout: number;
}

interface EnvConfig {
    development: Config;
    production: Config;
    test: Config;
}

const config: EnvConfig = {
    development: {
        baseURL: 'http://localhost:10010/api',
        timeout: 20000
    },
    production: {
        baseURL: 'http://xxx.xxx.xxx.xxx:10010/api',
        timeout: 20000
    },
    test: {
        baseURL: 'http://192.168.174.129:10010/api',
        timeout: 20000
    }
};

export const CONFIG:Config = config.development;



