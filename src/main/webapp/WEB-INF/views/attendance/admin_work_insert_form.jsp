<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceCheckDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 공통 템플릿 include -->
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/admin-sidebar.jsp">
    <jsp:param name="menu" value="attendance" />
</jsp:include>
<!-- 공통 템플릿 include -->

<head>
    <!-- 스타일 시트 -->
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/user-style.css' />">

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<title>근무 등록</title>
</head>

<body>
<div class="container">
    <h2>근무 등록</h2>

    <form action="<c:url value='/admin/work-insert' />" method="post">
        <label for="empId">사번:</label>
        <input type="text" id="empId" name="empId" required><br>

        <label for="workDate">근무 날짜:</label>
        <input type="date" id="workDate" name="workDate" required><br>

        <label for="workStatus">근무 상태:</label>
        <select id="workStatus" name="workStatus" required>
            <option value="출근">출근</option>
            <option value="지각">지각</option>
            <option value="조퇴">조퇴</option>
            <option value="외출">외출</option>
            <option value="결근">결근</option>
        </select><br>

        <label for="workTime">근무 시간 (예: 08:30~17:30):</label>
        <input type="text" id="workTime" name="workTime" required><br>

        <button type="submit">등록</button>
        <a href="<c:url value='/admin/work-list' />"><button type="button">취소</button></a>
    </form>
</div>

<jsp:include page="../common/footer2.jsp" />
</body>