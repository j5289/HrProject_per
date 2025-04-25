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

	<script>
        function confirmDelete(key) {
            if (confirm("해당 기록을 삭제하시겠습니까?")) {
                document.getElementById("deleteForm-" + key).submit();
            }
        }

        function showAlert(message) {
            alert(message);
        }
    </script>

    <title>근무 상태 목록</title>
</head>

<body>
<div class="container">
    <h2>사원 근무 조회</h2>

    <!-- 등록 버튼 -->
    <a href="<c:url value='/admin/work-insert-form' />">
        <button type="button">+ 근무 등록</button>
    </a>

    <!-- 필터 폼 -->
    <form action="<c:url value='/admin/work-list' />" method="get">
        <label for="empName">사원 이름:</label>
        <input type="text" id="empName" name="empName" value="${param.empName}">

        <label for="empId">사원 번호:</label>
        <input type="text" id="empId" name="empId" value="${param.empId}">

        <label for="startDate">시작 날짜:</label>
        <input type="date" id="startDate" name="startDate" value="${param.startDate}">

        <label for="endDate">종료 날짜:</label>
        <input type="date" id="endDate" name="endDate" value="${param.endDate}">

        <button type="submit">조회하기</button>
    </form>

    <!-- 근무 리스트 출력 -->
    <c:if test="${not empty workList}">
        <h3>조회 결과</h3>
        <table border="1">
            <thead>
            <tr>
                <th>사원명</th>
                <th>부서명</th>
                <th>근무일</th>
                <th>근무 상태</th>
                <th>근무 시간</th>
                <th>작업</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="work" items="${workList}">
                <tr>
                    <td>${work.empName}</td>
                    <td>${work.depName}</td>
                    <td>${work.attendanceDate}</td>
                    <td>${work.workStatus}</td>
                    <td>${work.workTime}</td>
                    <td>
                        <!-- 수정 버튼 -->
                        <a href="<c:url value='/admin/work-update-form/${work.empId}/${work.attendanceDate}' />">
                            <button type="button">수정하기</button>
                        </a>

                        <!-- 삭제 form -->
                        <form id="deleteForm-${work.empId}-${work.attendanceDate}" action="<c:url value='/admin/work-delete' />" method="post" style="display:inline;">
                            <input type="hidden" name="empId" value="${work.empId}" />
                            <input type="hidden" name="workDate" value="${work.attendanceDate}" />
                            <button type="button" onclick="confirmDelete('${work.empId}-${work.attendanceDate}')">삭제하기</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty workList}">
        <script>showAlert("해당하는 정보가 존재하지 않습니다.");</script>
    </c:if>
</div>

<jsp:include page="../common/footer2.jsp" />
</body>