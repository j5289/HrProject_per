<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itwill.attendance.dto.AttendanceCheckDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 공통 템플릿 include -->
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/user-sidebar.jsp">
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

    <title>출퇴근 기록부 및 현황</title>

<style>
.main-container {
  flex: 1;
  padding: 0;
  background-color: #f9f9f9;
}

.main-container form {
  max-width: 1000px;
  margin: auto;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.08);
}

@font-face {
  font-family: 'Pretendard';
  src: url('/resources/fonts/Pretendard-Regular.otf') format('opentype');
}

html, body {
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  background: #f9f9f9;
  margin: 0;
  padding: 0;
}

.my-content-wrapper {
  padding: 40px;
  background-color: #f9f9f9;
}

.my-content-wrapper h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
  font-weight: 600;
}

.my-content-wrapper h3 {
  font-size: 18px;
  margin-top: 30px;
  margin-bottom: 15px;
  font-weight: 600;
}

.my-content-wrapper h4 {
  font-size: 16px;
  margin: 20px 0 10px;
  font-weight: 500;
}

.my-content-wrapper form {
  max-width: 1000px;
  margin: auto;
  background: #fff;
  padding: 30px 40px;
  border-radius: 10px;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.08);
}

.my-content-wrapper .form-section {
  margin-bottom: 30px;
}

.my-content-wrapper label {
  display: block;
  font-weight: 500;
  margin: 18px 0 8px;
}

.my-content-wrapper .required {
  color: red;
  margin-right: 4px;
}

.my-content-wrapper input[type="date"],
.my-content-wrapper input[type="file"],
.my-content-wrapper select,
.my-content-wrapper textarea {
  width: 100%;
  padding: 10px;
  font-size: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
  margin-left: 15px;
}

.my-content-wrapper input[type="text"],
.my-content-wrapper select{
  width: 97%;
  padding: 10px;
  font-size: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
  margin-left: 15px;
}

.my-content-wrapper textarea {
  resize: vertical;
  min-height: 100px;
}

.my-content-wrapper input.half-width {
  width: 30%;
}

.my-content-wrapper input[type="radio"] {
  margin-right: 6px;
}

.my-content-wrapper .radio-group {
  display: flex;
  gap: 15px;
  margin-top: 10px;
}

.my-content-wrapper .radio-group label {
  display: flex;
  align-items: center;
  font-size: 15px;
}

.my-content-wrapper .fileList {
  margin-top: 10px;
  padding: 10px;
  background: #f3f3f3;
  border: 1px dashed #ccc;
  white-space: pre-wrap;
  font-size: 0.9em;
  color: #333;
}

.my-content-wrapper #approverResultTable {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}

.my-content-wrapper #approverResultTable th,
.my-content-wrapper #approverResultTable td {
  border: 1px solid #ddd;
  padding: 8px;
  font-size: 14px;
  text-align: center;
}

.my-content-wrapper .search-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}

.my-content-wrapper .search-input {
  width: 200px;
  padding: 8px;
  font-size: 14px;
}

.my-content-wrapper #selectedApprovers {
  list-style: none;
  padding-left: 0;
  margin-top: 15px;
  margin-left: 15px;
}

.my-content-wrapper #selectedApprovers li {
  background: #e9f0ff;
  padding: 6px 12px;
  margin-bottom: 6px;
  border-radius: 6px;
  font-size: 14px;
}

.my-content-wrapper #selectedApprovers li button {
  margin-left: 10px;
  background: transparent;
  border: none;
  color: red;
  font-size: 14px;
  cursor: pointer;
}

.my-content-wrapper #saveTemplateSection input {
  width: 250px;
  margin-right: 10px;
}

.my-content-wrapper .btn {
  padding: 8px 16px;
  font-size: 15px;
  background: #f0f0f0;
  color: black;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.my-content-wrapper .btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.my-content-wrapper #submitBtn:enabled {
  background-color: #007bff;
  color: white;
  transition: 0.2s;
}

.my-content-wrapper .form-footer {
  text-align: right;
  margin-top: 30px;
}

.search-wrapper button {
    padding: 8px 16px;
    font-size: 15px;
    background: #f0f0f0;
    color: black;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-bottom: 10px;
}
</style>


</head>

<body>
<div class="greeting-box" style="display: flex; flex-direction: column; align-items: center; margin: 20px 0;">
    <!-- 사원 사진 -->
    <img src="${pageContext.request.contextPath}/resources/img/profile.jpeg"
         alt="프로필 사진"
         style="width: 150px; height: 150px; object-fit: cover; border-radius: 15px; border: 2px solid #ccc; margin-bottom: 15px;" />
     
    <!-- 인사 문구 -->
    <div class="greeting-box" style="margin: 20px 0; font-size: 18px;">
        반갑습니다, <span style="font-weight: bold; font-size: 22px;">${empName}</span> 님!
    </div>
</div>

     
     
    <!-- 출근 버튼 -->
    <form>
        <input type="hidden" id="empId" value="${empId}" />
        <button type="button" id="checkInButton">출근하기</button>
    </form>

    <!-- 퇴근 버튼 -->
    <form>
        <input type="hidden" id="empId" value="${empId}" />
        <button type="button" id="checkOutButton">퇴근하기</button>
    </form>

    <!-- 상태 메시지 및 시간 출력 -->
    <div id="statusMessage"></div>
    <div>출근 시간: <span id="checkInTime"></span></div>
    <div>퇴근 시간: <span id="checkOutTime"></span></div>

    <!-- 출퇴근 기록 조회 -->
    <form id="check-attendance-form">
        <input type="text" name="empId" placeholder="사원 ID" />
        <input type="date" name="workDate" />
        <button type="submit">출퇴근 기록 조회</button>
    </form>

  <script>
        $(document).ready(function () {
        	//출근 처리 
        	$('#checkInButton').click(function() {
		    $.ajax({
		        url: '/attendance/check-in',
		        type: 'POST',
		        data: {
		            empId: $('#empId').val()
		        },
		        dataType: 'json',
		        success: function(response) {
		            console.log("출근 서버 응답:", response);
		            $('#statusMessage').text(response.message);
		            if (response.checkInTime) {
		                $('#checkInTime').text(response.checkInTime);
		            }
		        },
		        error: function(xhr, status, error) {
		            console.error("출근 에러", xhr, status, error);
		            alert("출근 처리에 실패했습니다.\n" + (xhr.responseText || "서버 오류"));
		        }
		    });
		});
			
        	//퇴근 처리
            $('#checkOutButton').click(function() {
                $.ajax({
                    url: '/attendance/check-out',
                    type: 'POST',
                    dataType: 'json',
                    success: function(response) {
                        $('#statusMessage').text(response.message); // 메시지 출력
                        if (response.checkInTime) {
                            $('#checkInTime').text(response.checkInTime); // 출근 시간 출력
                        }
                        if (response.checkOutTime) {
                            $('#checkOutTime').text(response.checkOutTime); // 퇴근 시간 출력
                        }
                    },
                    error: function() {
                        alert("퇴근 처리 중 오류가 발생했습니다.");
                    }
                });
            });

            // 특정 날짜 출퇴근 기록 조회
	            $("#check-attendance-form").submit(function (event) {
		        event.preventDefault();
		        var empId = $("input[name='empId']").val();
		        var workDate = $("input[name='workDate']").val();
		
		        $.ajax({
		            url: "/attendance/check-attendance",
		            type: "POST",
		            data: { empId: empId, workDate: workDate },
		            success: function (response) {
		                if (response.attendance) {
		                    alert(response.message);
		                    console.log(response.attendance);
		                } else {
		                    alert(response.message);
		                }
		            },
		            error: function () {
		                alert("출퇴근 기록 조회에 실패했습니다.");
		            }
                });
            });
        });
    </script>

<jsp:include page="../common/footer2.jsp" />
</body>