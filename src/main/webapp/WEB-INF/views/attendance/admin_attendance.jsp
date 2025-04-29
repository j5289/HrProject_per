<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceAdminCheckDTO" %>
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

    <title>사원 출퇴근 기록 조회</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            margin: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #aaa;
            text-align: center;
        }
        .no-result {
            margin-top: 20px;
            color: red;
            font-weight: bold;
        }
        .search-box {
            margin-bottom: 20px;
        }
        .search-box input {
            padding: 8px;
            margin-right: 10px;
        }
        .search-box button {
            padding: 8px 15px;
        }
    </style>
    
</head>

<body>
 <div class="container">
        <h2>사원 출퇴근 기록 조회</h2>

        <!-- 검색 폼 -->
        <form action="list" method="get" class="search-box">
            <input type="text" name="empName" placeholder="사원 이름" value="${empName}" />
            <input type="date" name="workDate" placeholder="날짜" value="${workDate}" />
            <button type="submit">조회하기</button>
        </form>

        <c:choose>
            <c:when test="${not empty attendanceList}">
                <table>
                    <thead>
                        <tr>
                            <th>사원 번호</th>
                            <th>사원 이름</th>
                            <th>출근 시간</th>
                            <th>퇴근 시간</th>
                            <th>근무 상태</th>
                            <th>지각 여부</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="attendance" items="${attendanceList}">
                            <tr>
                                <td>${attendance.empId}</td>
                                <td>${attendance.empName}</td>
                                <td>${attendance.checkInTime}</td>
                                <td>${attendance.checkOutTime}</td>
                                <td>${attendance.workStatus}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${attendance.isLate}">지각</c:when>
                                        <c:otherwise>정상 출근</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="no-result">조회된 출퇴근 기록이 없습니다.</div>
            </c:otherwise>
        </c:choose>
    </div>

<jsp:include page="../common/footer2.jsp" />
</body>
