# Task-MiraiPlugin
一个管理事务的

[mirai-console]: https://github.com/mamoe/mirai-console

插件

## 功能

定时事务，即到点提醒的功能。

## 使用

首先要让机器人知道哪些群，哪些人是需要启用事务功能的

在Config文件中设置

要添加事务，使用

```
添加事务 时间 事务内容
```

要删除事务，使用

```
删除事务 时间
```

要查看自己的所有事务，使用

```
列出事务
```



以上所有开头的命令都可以在Config文件中自定义

## 待做的功能

周期性的事务提醒（如每日提醒，每月提醒等）