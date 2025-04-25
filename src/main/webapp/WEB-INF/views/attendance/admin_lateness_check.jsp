<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceAdminLateDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

    <title>사원 지각 조회</title>
    
    <script>
        // 지각 내역이 없을 경우 '해당 정보가 없습니다.'
        function checkAttendanceData() {
            var attendanceList = document.getElementById("attendanceTable").rows.length;
            if (attendanceList <= 1) {
                alert("해당 정보가 없습니다.");
            }
        }
    </script>
</head>

<body>

    <h2>사원 지각 현황 조회</h2>

    <!-- 검색 폼 -->
    <form action="/admin/attendance/late-list" method="get">
        <label for="empName">사원 이름:</label>
        <input type="text" id="empName" name="empName" placeholder="사원 이름" value="${param.empName}" />
        <br><br>

        <label for="empId">사원 번호:</label>
        <input type="text" id="empId" name="empId" placeholder="사원 번호" value="${param.empId}" />
        <br><br>

        <button type="submit">조회하기</button>
    </form>

    <br>

    <!-- 지각 내역 테이블 -->
    <c:if test="${not empty lateList}">
        <table border="1" id="attendanceTable">
            <thead>
                <tr>
                    <th>사원 ID</th>
                    <th>사원 이름</th>
                    <th>근무 날짜</th>
                    <th>지각 날짜 목록</th>
                    <th>지각 횟수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="late" items="${lateList}">
                    <tr>
                        <td>${late.empId}</td>
                        <td>${late.empName}</td>
                        <td>${late.workDate}</td>
                        <td>
                            <!-- 지각 날짜 목록 출력 -->
                            <c:if test="${not empty late.latenessDates}">
                                ${late.latenessDates}
                            </c:if>
                            <c:if test="${empty late.latenessDates}">
                                없음
                            </c:if>
                        </td>
                        <td>${late.latenessCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <script>checkAttendanceData();</script> <!-- 조회 후 지각 내역이 없으면 알림 표시 -->
    </c:if>

    <!-- 지각 내역이 없을 경우 -->
    <c:if test="${empty lateList}">
        <script>
            alert("해당 정보가 없습니다.");
        </script>
    </c:if>

    <!-- 공통 템플릿 include -->
    <jsp:include page="../common/footer2.jsp" />
</body>
</html>
