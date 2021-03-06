﻿#Readme

tags： 面向对象课程文档 Java

---

[TOC]

###电梯调度策略和程序功能说明

电梯调度策略和作业指导书上一致

###程序运行所需环境和运行指令规范

    Java version "1.8.0_101"
    Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
    Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
    Eclipse IDE for Java Developers	Version: Neon Release (4.6.0)
    程序输入由Eclipse控制台输入

###程序输入说明，包括标准输入格式、输入限制和遇见输入错误时的响应信息

**1. 标准输入格式**

* 标准输入的格式要求与作业指导书上的相同
* 用户输入为按照请求产生时间排序的字符串
* 楼层请求格式为：(FR,m,UP/DOWN,T)，其中FR为标识，m为发出请求的楼层号，UP为向上请求，DOWN 为向下请求，T为发出时刻；电梯内请求格式为：(ER,n,T)，其中 ER 为标识，n 为请求去往的目标楼层号，T 为发出时刻
* 所有字符均为英文状态下的字符 `要求测试者保证`
* 本程序输入的请求之间使用换行分隔，即输入一个请求之后换行输入下一个请求，如果输入的请求存在格式错误或者输入请求的时间乱序，程序将会提示请求无效，但仍可以继续输入请求，当所有请求输入完毕后，在新的一行输入run并回车，电梯将会执行调度，相同请求的输出将在电梯调度过程中完成 
* 第一个请求必须为 (FR,1,UP,0)
* 合法的请求产生时刻为非负整数（范围 0-2^31-1，支持前导 0 和正号），n，m 为 1~10 之间含两端的正整数（支持前导 0 和正号）
* 不正确的标识符，不正确的方向，不正确的数字范围，多余的其他非允许字符，均认定为不合法输入，即无效输入
* 特别地，对于 FR 标识符，1 楼的 DOWN 和 10 楼的 UP 也认为是无效输入

**2. 输入限制**

* 输入一条请求之后应该换行继续下一条输入，在换行前输入多余字符都会被认为该行的请求无效，当请求输入完毕之后在新的一行输入run即可执行调度 `如果一次复制一大段请求粘贴是不符合本程序输入规范的 可能产生意外错误`
* 第一条有效请求必须为 (FR,1,UP,0)，若前面的请求都是无效的，下一条请求必须为 (FR,1,UP,0) 才能算是有效的
* 程序支持前导 0 和正号，但是不支持重复的符号，即 ++ 等，也不支持负号
* 程序将会自动忽略所有空格，但是诸如制表符之类的不被认为是空格而是非允许字符

**3. 遇见输入错误时的响应信息**

* 程序对于格式错误或者输入时间乱序的请求的响应与作业指导书上相同，格式为 `INVALID [request]`
* 程序对于提示的无效请求不会执行
* 输入的一行为空也被认为是无效输入
* 若输入的没有有效请求，则程序在输入run之后没有输出
* 对于格式错误的请求，[request]内为最初请求的字符串，如 `[(ER,2,-1)]` 或   `[(FR,1,,0]`；对于输入请求的时间序列不符合要求的，[request]内是不带有"()"的请求，如 `[FR,1,UP,2]`

###程序计算结果的输出规格，以及可预见的运行错误响应信息

* 每个有效请求执行完毕的输出请求内容和请求执行结果与作业指导书上相同
* 对于相同请求，程序输出格式为 `SAME [request]`
* 如果一次停靠执行了多条请求，请求会分行输出，但是对于多条实质上相同的捎带请求，程序只会输出第一条请求，其他的会被当做相同请求输出，例如
``` 
    (FR,1,UP,0)
    (ER,8,0)
    (ER,6,1)
    (ER,6,2)
    (FR,6,UP,2)
    /*
    其中 (ER,6,1) 和 (ER,6,2) 只会输出 (ER,6,1) 而 (ER,6,2) 会被当做 SAME 输出，即输出：
        [ER,6,1]/(6,UP,3.5)
        SAME [ER,6,2]
        [FR,6,UP,2]/(6,UP,3.5)
    */
```
* 最终结果输出时如果时间较大会以科学计数法的形式输出，程序经过测试不会丢失精度
* 对于格式没有发生错误的请求，输出结果中的 [request] 内的数字都是去掉前导 0 和符号的
* 原则上不允许输入Ctrl-Z，如果输入Ctrl-Z，程序会结束运行并输出 `INVALID [Ctrl-Z]`



