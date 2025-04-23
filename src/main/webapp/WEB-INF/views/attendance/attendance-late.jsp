<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceLateDTO" %>
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

    <title>지각 현황</title>

    <script>
        function loadLateStatus() {
            var empId = $('#empId').val();
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            
            $.ajax({
                url: "<c:url value='/attendance/late-status' />", // 컨트롤러 경로
                type: "POST",
                data: {
                    empId: empId, // 사원 ID
                    startDate: startDate,
                    endDate: endDate
                },
                success: function(response) {
                    // 지각 내역 출력
                    var lateDetails = response.lateDetails;
                    var lateStats = response.lateStats;

                    // 지각 내역 출력
                    var tableContent = '';
                    if (lateDetails.length > 0) {
                        $.each(lateDetails, function(index, record) {
                            tableContent += '<tr>';
                            tableContent += '<td>' + record.startDate + '</td>';
                            tableContent += '<td>' + record.latenessMinutes + '</td>';
                            tableContent += '<td>' + record.reason + '</td>';
                            tableContent += '<td>' + record.managerCheck + '</td>';
                            tableContent += '<td>' + record.fimeName + '</td>';
                            tableContent += '</tr>';
                        });
                    } else {
                        tableContent = '<tr><td colspan="5">해당 기간 내 지각 정보가 없습니다.</td></tr>';
                    }
                    $('#lateDetailsTable tbody').html(tableContent);

                    // 지각 통계 출력
                    var totalLateCount = lateStats.totalLateCount;
                    var totalLateMinutes = lateStats.totalLateMinutes;
                    $('#totalLateCount').text(totalLateCount);
                    $('#totalLateMinutes').text(totalLateMinutes);
                },
                error: function() {
                    alert('지각 현황 조회에 실패했습니다.');
                }
            });
        }

        // 페이지 로드 시 자동으로 지각 현황 조회
        $(document).ready(function() {
            $('#fetchLateStatusBtn').on('click', function() {
                loadLateStatus();
            });
        });
    </script>
</head>

<body>
    <div class="container">
        <div class="main-content">
            <h1>지각 현황</h1>

            <!-- 지각 현황 조회 폼 -->
            <form id="lateStatusForm">
                <label for="empId">사원 ID:</label>
                <input type="text" id="empId" name="empId">
                <label for="startDate">조회 시작 날짜:</label>
                <input type="date" id="startDate" name="startDate">
                <label for="endDate">조회 종료 날짜:</label>
                <input type="date" id="endDate" name="endDate">
                <button type="button" id="fetchLateStatusBtn">조회하기</button>
            </form>

            <!-- 지각 통계 -->
            <div>
                <h3>지각 통계</h3>
                <p>지각 횟수: <span id="totalLateCount">0</span></p>
                <p>총 지각 시간: <span id="totalLateMinutes">0</span> 분</p>
            </div>

            <!-- 지각 내역 테이블 -->
            <table id="lateDetailsTable" border="1">
                <thead>
                    <tr>
                        <th>지각 날짜</th>
                        <th>지각 시간(분)</th>
                        <th>지각 사유</th>
                        <th>관리자 확인 상태</th>
                        <th>사유서</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- AJAX 응답으로 지각 내역이 여기에 동적으로 출력 -->
                </tbody>
            </table>
        </div>
    </div>

    <jsp:include page="../common/footer2.jsp" />
</body>
