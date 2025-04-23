<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceStatusDTO" %>
<!-- 템플릿 include -->
<!-- http://localhost:8088/attendance/attendance_main -->
<!-- jQuery 라이브러리 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">

    <title>근태 관리 메인 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
    <script>
    $(document).ready(function(){
        $('#checkInBtn').click(function(event){
            event.preventDefault(); // 폼 제출을 막고 Ajax로 처리

            var empId = $('#empId').val(); // hidden 필드로 empId 받아오기

            $.ajax({
                type: 'POST',
                url: '/attendance/check-in',  // 출근 처리 요청 경로
                data: {
                    empId: empId  // 서버로 전달할 데이터
                },
                success: function(response){
                    // 성공적으로 출근 등록이 완료되면, 페이지에 업데이트
                    $('#statusMessage').text("출근 완료!");  // 결과 메시지 갱신
                    $('#checkInTime').text(response.checkInTime);  // 출근 시간이면 페이지에서 갱신
                },
                error: function(){
                    $('#statusMessage').text("출근 처리에 실패했습니다. 다시 시도해주세요.");
                }
            });
        });

        // 퇴근 버튼 처리 (동일한 방식으로 적용 가능)
        $('#checkOutBtn').click(function(event){
            event.preventDefault();

            var empId = $('#empId').val();

            $.ajax({
                type: 'POST',
                url: '/attendance/check-out',  // 퇴근 처리 요청 경로
                data: {
                    empId: empId
                },
                success: function(response){
                    $('#statusMessage').text("퇴근 완료!");  // 결과 메시지 갱신
                    $('#checkOutTime').text(response.checkOutTime);  // 퇴근 시간이면 페이지에서 갱신
                },
                error: function(){
                    $('#statusMessage').text("퇴근 처리에 실패했습니다. 다시 시도해주세요.");
                }
            });
        });
    });
    
 // 3. 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    $("#check-attendance-form").submit(function(event) {
        event.preventDefault(); // 폼 전송 기본 동작을 막음
        var empId = $("input[name='empId']").val();
        var workDate = $("input[name='workDate']").val();
        
        $.ajax({
            url: "/attendance/check-attendance",  // 출퇴근 기록 조회 URL
            type: "POST",
            data: { empId: empId, workDate: workDate },
            success: function(response) {
                if (response.attendance) {
                    // 출퇴근 기록 조회 성공, 데이터 처리
                    console.log(response.attendance); // 조회된 출퇴근 기록
                    alert(response.message); // 성공 메시지
                } else {
                    alert(response.message); // 실패 메시지
                }
            },
            error: function() {
                alert("출퇴근 기록 조회에 실패했습니다.");
            }
        });
    });

</script>

<!-- 출근 버튼 -->
<form>
    <input type="hidden" id="empId" value="${sessionScope.loginEmp.empId}" />
    <button type="button" id="checkInBtn">출근하기</button>
</form>

<!-- 퇴근 버튼 -->
<form>
    <input type="hidden" id="empId" value="${sessionScope.loginEmp.empId}" />
    <button type="button" id="checkOutBtn">퇴근하기</button>
</form>

<!-- 상태 메시지 출력 -->
<div id="statusMessage"></div>

<!-- 출근 시간 출력 -->
<div>출근 시간: <span id="checkInTime"></span></div>

<!-- 퇴근 시간 출력 -->
<div>퇴근 시간: <span id="checkOutTime"></span></div>
