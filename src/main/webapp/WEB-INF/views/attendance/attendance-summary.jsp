<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceWorkCheckDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 공통 템플릿 include -->
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/admin-sidebar.jsp">
    <jsp:param name="menu" value="attendance" />
</jsp:include>
<!-- 공통 템플릿 include -->

<%
    // 세션 로그인 체크
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

    <title>근무 조회</title>

    <script>
        $(document).ready(function() {
            $('#searchButton').click(function() {
                // 폼 데이터 가져오기
                var empId = "<%= empId %>";
                var startDate = $('#startDate').val();
                var endDate = $('#endDate').val();

                // AJAX 요청 보내기
                $.ajax({
                    url: '/attendance/work-summary',
                    method: 'POST',
                    data: {
                        empId: empId,
                        startDate: startDate,
                        endDate: endDate
                    },
                    success: function(response) {
                        if (response.workSummary) {
                            // 근무 통계가 존재할 경우
                            $('#workSummary').html(`
                                <h3>근무 통계</h3>
                                <p>누적 근무 일수: ${response.workSummary.totalDate}일</p>
                                <p>누적 근무 시간: ${response.workSummary.totalTime}시간</p>
                                <p>일반 근무 일수: ${response.workSummary.workDays}일</p>
                                <p>일반 근무 시간: ${response.workSummary.workHours}시간</p>
                                <p>지각 시간: ${response.workSummary.latenessMinutes}분</p>
                                <p>휴가일수: ${response.workSummary.leaveDays}일</p>
                            `);
                        } else {
                            // 근무 통계가 없을 경우
                            $('#workSummary').html('<p>해당 근무 정보가 존재하지 않습니다.</p>');
                        }
                    },
                    error: function() {
                        $('#workSummary').html('<p>근무 통계 조회에 실패했습니다.</p>');
                    }
                });
            });
        });
    </script>
</head>

<body>
    <!-- 조회 폼 -->
    <div class="container">
        <h2>근무 조회</h2>
        <form id="attendanceForm">
            <div>
                <label for="startDate">시작 날짜:</label>
                <input type="date" id="startDate" name="startDate" required />
            </div>
            <div>
                <label for="endDate">끝 날짜:</label>
                <input type="date" id="endDate" name="endDate" required />
            </div>
            <button type="button" id="searchButton">조회하기</button>
        </form>
    </div>

    <!-- 조회 결과 -->
    <div id="workSummary" class="result">
        <!-- 근무 통계 결과가 이곳에 출력됩니다 -->
    </div>

    <jsp:include page="../common/footer2.jsp" />
</body>