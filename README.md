# RuoYi-Cloud-eruka
# 微服务 eruka为注册中心
# linunx指定内存启动程序 nohup java -Xms64m -Xmx128m -jar ruoyi-system-1.1.0-SNAPSHOT.jar &


清理前内存使用情况 

free -m
释放缓存前同步

sync
释放所有缓存

echo 3 > /proc/sys/vm/drop_caches
释放缓存数字参数说明

0 – 不释放
1 – 释放页缓存
2 – 释放dentries和inodes
3 – 释放所有缓存