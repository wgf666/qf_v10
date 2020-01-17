<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
Hello,${username}

<span>展示学生信息</span>
学号:${student.id}
姓名:${student.name}
生日:${student.birthday?date}
生日:${student.birthday?time}
生日:${student.birthday?datetime}

<span>展示学生信息列表</span>
<table>
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>学生</td>
    </tr>
    <#list list as stu>
        <tr>
            <td>${stu.id}</td>
            <td>${stu.name}</td>
            <td>${stu.birthday?datetime}</td>
        </tr>
    </#list>

    <sapn>条件判断</sapn>
    <#if (age > 40)>
    中年
    <#elseif (age>30) >
    青年
    <#else >
    少年
    </#if>

    ${msg!''}
    ${msg!'此信息为空'}
</table>
</body>
</html>