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

    <title>사원 휴가 내역 조회</title>
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
        <h2>사원 휴가 내역 조회</h2>

        <form action="leave-check" method="get" class="search-box">
            <input type="text" name="empId" placeholder="사원 번호" value="${param.empId}" />
            <input type="text" name="empName" placeholder="사원 이름" value="${param.empName}" />
            <button type="submit">조회하기</button>
        </form>

        <c:choose>
            <c:when test="${not empty leaveList}">
                <table>
                    <thead>
                        <tr>
                            <th>사원 번호</th>
                            <th>사원 이름</th>
                            <th>휴가 신청일</th>
                            <th>휴가 시작일</th>
                            <th>휴가 종료일</th>
                            <th>휴가 종류</th>
                            <th>보고서</th>
                            <th>승인 상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="leave" items="${leaveList}">
                            <tr>
                                <td>${leave.empId}</td>
                                <td>${leave.empName}</td>
                                <td>${leave.uploadDate}</td>
                                <td>${leave.leaveStartDate}</td>
                                <td>${leave.leaveEndDate}</td>
                                <td>${leave.leaveStatus}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty leave.fileName}">
                                            <a href="${leave.filePath}/${leave.fileName}" target="_blank">보고서 보기</a>
                                        </c:when>
                                        <c:otherwise>
                                            없음
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${leave.approvalConfirmed == 'Y'}">승인</c:when>
                                        <c:when test="${leave.approvalConfirmed == 'N'}">반려</c:when>
                                        <c:otherwise>대기</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <!-- 메세지 출력 -->
                <div class="no-result">${message}</div>
            </c:otherwise>
        </c:choose>
    </div>
<jsp:include page="../common/footer2.jsp" />
</body>
