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

	<title>근무 정보 수정</title>
</head>

<body>
<div class="container">
    <h2>근무 정보 수정</h2>

    <form action="<c:url value='/admin/work-update' />" method="post">
        <!-- 사번: 수정 불가 -->
        <label for="empId">사번:</label>
        <input type="text" id="empId" name="empId" value="${work.empId}" readonly><br>

        <!-- 날짜: 수정 불가 -->
        <label for="workDate">근무 날짜:</label>
        <input type="date" id="workDate" name="workDate" value="${work.attendanceDate}" readonly><br>

        <!-- 근무 상태 -->
        <label for="workStatus">근무 상태:</label>
        <select id="workStatus" name="workStatus" required>
            <option value="출근" ${work.workStatus eq '출근' ? 'selected' : ''}>출근</option>
            <option value="지각" ${work.workStatus eq '지각' ? 'selected' : ''}>지각</option>
            <option value="조퇴" ${work.workStatus eq '조퇴' ? 'selected' : ''}>조퇴</option>
            <option value="외출" ${work.workStatus eq '외출' ? 'selected' : ''}>외출</option>
            <option value="결근" ${work.workStatus eq '결근' ? 'selected' : ''}>결근</option>
        </select><br>

        <!-- 근무 시간 -->
        <label for="workTime">근무 시간 (예: 08:30~17:30):</label>
        <input type="text" id="workTime" name="workTime" value="${work.workTime}" required><br>

        <!-- 버튼 -->
        <button type="submit">수정 완료</button>
        <a href="<c:url value='/admin/work-list' />"><button type="button">취소</button></a>
    </form>
</div>

<jsp:include page="../common/footer2.jsp" />
</body>