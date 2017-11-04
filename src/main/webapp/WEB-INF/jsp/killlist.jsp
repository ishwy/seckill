<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shwy
  Date: 2017/11/1
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀成功列表页</title>
    <%@include file="common/tag.jsp"%>
    <%@include file="common/header.jsp"%>
</head>
<body>
        <%-- 页面暂时部分 --%>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2>秒杀成功列表页</h2>
                </div>

                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>秒杀id</th>
                                <th>秒杀号码</th>
                                <th>状态</th>
                                <th>创建时间</th>
                            </tr>
                        </thead>
                        <tbody>

                        <C:forEach var="sk" items="${killlist}">
                            <tr>
                                <td>${sk.seckillId}</td>
                                <td>${sk.userPhone}</td>
                                <C:choose>
                                    <C:when test="${sk.state == -1}">
                                            <td>无效</td>
                                    </C:when>
                                    <C:when test="${sk.state ==  0}">
                                            <td>成功</td>
                                    </C:when>
                                    <C:when test="${sk.state ==  1}">
                                            <td>已付款</td>
                                    </C:when>
                                </C:choose>

                                <td>
                                    <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>

                        </C:forEach>


                        </tbody>
                    </table>
                </div>
            </div>
        </div>
</body>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>