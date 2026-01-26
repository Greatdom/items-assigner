@echo off
chcp 65001 >nul 2>&1
title 开发环境一键启动脚本
echo ==============================================
echo 开始启动开发环境组件...
echo ==============================================

echo.
echo [1/7] 正在启动Redis服务...
start "Redis Server" "G:\Redis-8.2.0-Windows-x64-cygwin-with-Service\Redis-8.2.0-Windows-x64-cygwin-with-Service\redis-server.exe"
if %errorlevel% equ 0 (
    echo Redis服务启动命令已执行
) else (
    echo 警告：Redis服务启动可能失败，请检查路径是否正确！
)
timeout /t 3 /nobreak >nul 2>&1

echo.
echo [2/7] 正在启动Nacos（单机模式）...
cd /d "G:\nacos-2.3.0\bin"
if %errorlevel% equ 0 (
    start "Nacos Server" cmd /k "startup.cmd -m standalone"
    echo Nacos启动命令已执行
) else (
    echo 错误：无法进入Nacos的bin目录，请检查路径是否正确！
    pause
    exit /b 1
)
timeout /t 5 /nobreak >nul 2>&1

echo.
echo [3/7] 正在启动Navicat Premium 16...
start "" "E:\WEB_ENV\Navicat Premium 16.lnk"
if %errorlevel% equ 0 (
    echo Navicat启动命令已执行
) else (
    echo 警告：Navicat启动可能失败，请检查快捷方式路径是否正确！
)
timeout /t 2 /nobreak >nul 2>&1

echo.
echo [4/7] 正在启动Postman...
start "" "E:\WEB_ENV\Postman.lnk"
if %errorlevel% equ 0 (
    echo Postman启动命令已执行
) else (
    echo 警告：Postman启动可能失败，请检查快捷方式路径是否正确！
)
timeout /t 2 /nobreak >nul 2>&1

echo.
echo [5/7] 正在启动Redis界面工具...
start "" "E:\WEB_ENV\Redis界面.lnk"
if %errorlevel% equ 0 (
    echo Redis界面工具启动命令已执行
) else (
    echo 警告：Redis界面工具启动可能失败，请检查快捷方式路径是否正确！
)
timeout /t 2 /nobreak >nul 2>&1

echo.
echo [6/7] 正在启动Seata服务...
set "seata_bin_dir=G:\seata-server-1.4.2\seata-server-1.4.2\bin"
set "seata_bat=%seata_bin_dir%\seata-8.bat"
if exist "%seata_bat%" (
    cd /d "%seata_bin_dir%"
    if %errorlevel% equ 0 (
        start "Seata Server" cmd /k "%seata_bat%"
        echo Seata服务启动命令已执行
    ) else (
        echo 错误：无法进入Seata的bin目录 %seata_bin_dir%！
    )
) else (
    echo 错误：未找到Seata脚本 %seata_bat%，请检查路径是否正确！
)

echo.
echo [7/7] 正在启动RabbitMQ服务...
set "vbs_file=%temp%\rabbitmq_auto_admin.vbs"
echo Set UAC = CreateObject^("Shell.Application"^) > "%vbs_file%"
echo UAC.ShellExecute "cmd.exe", "/c sc start RabbitMQ && pause", "", "runas", 1 >> "%vbs_file%"
cscript //nologo "%vbs_file%"
del /f /q "%vbs_file%" >nul 2>&1
echo RabbitMQ服务启动命令已提交（管理员权限）
timeout /t 3 /nobreak >nul 2>&1

echo.
echo ==============================================
echo 所有组件启动命令已执行完成！
echo 注意：部分组件（如Nacos/Seata）启动需要时间，请耐心等待。
echo ==============================================
pause