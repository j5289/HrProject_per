<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceCheckDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 공통 템플릿 include -->
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/admin-sidebar.jsp">
    <jsp:param name="menu" value="attendance" />
</jsp:include>
<!-- 공통 템플릿 include -->

<%
    //세션 로그인 체크
    String empId = (String) session.getAttribute("id");
    if (empId == null) {
        response.sendRedirect(request.getContextPath() + "/member/login");  // 컨트롤러 호출 → 뷰 리졸버 통해 JSP 열림
        return;
    }
    com.itwill.approval.dto.ApprovalSearchDTO loginUser = new com.itwill.approval.dto.ApprovalSearchDTO();
    loginUser.setEmpId(empId);
%>

<head>
    <!-- 스타일 시트 -->
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/user-style.css' />">

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    
</head>
</head>
<body>
<h2>전체 사원 출결 현황</h2>

<form method="get" action="/admin/attendance/list">
    사원 이름: <input type="text" name="empName" value="${empName}" />
    날짜 선택: <input type="date" name="workDate" value="${workDate}" />
    <button type="submit">조회</button>
</form>

<c:choose>
    <!-- 검색 결과가 없을 경우 -->
    <c:when test="${empty attendanceList}">
        <script>
            alert('해당 기록이 존재하지 않습니다.');
        </script>
    </c:when>

    <!-- 검색 결과가 있을 경우 -->
    <c:otherwise>
        <table border="1">
            <thead>
                <tr>
                    <th>사원 ID</th>
                    <th>사원 이름</th>
                    <th>부서명</th>
                    <th>출근 시간</th>
                    <th>퇴근 시간</th>
                    <th>근무 상태</th>
                    <th>지각 여부</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${attendanceList}">
                    <tr>
                        <td>${a.employeeId}</td>
                        <td>${a.employeeName}</td>
                        <td>${a.departmentName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty a.checkInTime}">
                                    ${a.checkInTime}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty a.checkOutTime}">
                                    ${a.checkOutTime}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${a.workStatus}</td>
                        <td>
                            <c:choose>
                                <c:when test="${a.isLate}">지각</c:when>
                                <c:otherwise>정상</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer2.jsp" />
</body>