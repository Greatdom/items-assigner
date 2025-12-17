---@diagnostic disable: undefined-global
-- 灵活验证码校验脚本（支持单/双校验，双校验全部成功才删Key）
-- 入参规则：
-- ========== 单校验场景 ==========
-- KEYS[1]：校验类型前缀（"verify:phone:" / "verify:email:"）
-- ARGV[1]：账号（手机号/邮箱）
-- ARGV[2]：用户输入的验证码
-- ARGV[3]：校验模式（"single"）
-- 返回值：字符串（"1"=成功，"0"=失败）

-- ========== 双校验场景 ==========
-- KEYS[1]：手机号前缀（"verify:phone:"）
-- KEYS[2]：邮箱前缀（"verify:email:"）
-- ARGV[1]：手机号
-- ARGV[2]：手机验证码
-- ARGV[3]：邮箱
-- ARGV[4]：邮箱验证码
-- ARGV[5]：校验模式（"double"）
-- 返回值：table {"1"/"0", "1"/"0"}（手机/邮箱校验结果）

-- 私有函数：读取存储的验证码（通用逻辑）
local function getStoredCode(keyPrefix, account)
    local key = keyPrefix .. account
    return redis.call('GET', key), key
end

-- 私有函数：单校验逻辑
local function singleCheck()
    local prefix = KEYS[1]
    local account = ARGV[1]
    local inputCode = ARGV[2]

    local storedCode, key = getStoredCode(prefix, account)
    if not storedCode then
        return "0"  -- 无验证码，返回失败
    end
    -- 单校验匹配成功则立即删Key
    if storedCode == inputCode then
        redis.call('DEL', key)
        return "1"  -- 成功返回"1"
    end
    return "0"  -- 验证码不匹配，返回失败
end

-- 私有函数：双校验逻辑（全部成功才删Key）
local function doubleCheck()
    local phonePrefix = KEYS[1]
    local emailPrefix = KEYS[2]
    local phone = ARGV[1]
    local phoneCode = ARGV[2]
    local email = ARGV[3]
    local emailCode = ARGV[4]

    -- 1. 读取两个验证码和对应的Key
    local storedPhoneCode, phoneKey = getStoredCode(phonePrefix, phone)
    local storedEmailCode, emailKey = getStoredCode(emailPrefix, email)

    -- 2. 校验结果（不存在直接判定失败）
    local phoneMatch = storedPhoneCode and storedPhoneCode == phoneCode
    local emailMatch = storedEmailCode and storedEmailCode == emailCode

    -- 3. 仅当两个都匹配成功，才批量删除Key
    if phoneMatch and emailMatch then
        redis.call('DEL', phoneKey)
        redis.call('DEL', emailKey)
    end

    -- 4. 返回各自的校验结果（字符串）
    return {phoneMatch and "1" or "0", emailMatch and "1" or "0"}
end

-- 主逻辑：根据模式分发
local checkMode = ARGV[#ARGV] -- 最后一个参数为校验模式
if checkMode == "single" then
    return singleCheck()
elseif checkMode == "double" then
    return doubleCheck()
else
    return "0" -- 非法模式返回失败
end