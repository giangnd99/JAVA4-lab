<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Người Dùng</title>
</head>
<body>
<h2>Danh sách Người Dùng</h2>

<!-- Form Lọc -->
<form action="lab1" method="get">
    <label for="filter">Lọc theo:</label>
    <select name="filter" id="filter">
        <option value="">Tất cả Người Dùng</option>
        <option value="email" ${filter == 'email' ? 'selected' : ''}>Email kết thúc bằng @fpt.edu.vn</option>
    </select>
    <button type="submit">Áp dụng Lọc</button>
</form>

<!-- Bảng Người Dùng -->
<table border="1">
    <tr>
        <th>Tên</th>
        <th>Email</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.fullname}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
</table>

<!-- Điều Khiển Phân Trang -->
<c:if test="${totalPages > 1}">
    <nav>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="lab1?pageNumber=${i}&filter=${filter}">${i}</a>
        </c:forEach>
    </nav>
</c:if>
</body>
</html>
