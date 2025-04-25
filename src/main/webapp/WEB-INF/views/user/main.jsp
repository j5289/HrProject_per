<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/user-sidebar.jsp">
    <jsp:param name="menu" value="dashboard" />
</jsp:include>
<!-- 모달창 -->
<div class="modal fade" id="calendarModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">일정 등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" id="modalDate">
        <input type="text" id="calTitle" class="form-control" placeholder="일정 제목 입력">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="saveEvent()">저장</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>


<div class="content">
    <div class="main-column">
        <!-- 프로필 카드 -->
        <div class="profile-card">
            <div class="profile-header">
                <img src="<c:url value='/resources/img/profile.jpeg' />" alt="프로필 이미지" class="profile-image">
                <div class="profile-info">
                    <h3>${employee.empName}</h3>
                    <p>${employee.depName} / ${employee.rankId}</p>
                    <p>사원번호: ${employee.empId}</p>
                </div>
            </div>
            <div class="profile-details">
                <div class="detail-item">
                    <span class="label">이메일:</span>
                    <span class="value">${employee.empEmail}</span>
                </div>
                <div class="detail-item">
                    <span class="label">연락처:</span>
                    <span class="value">${employee.empPhone}</span>
                </div>
                <div class="detail-item">
                    <span class="label">입사일:</span>
                    <span class="value"><fmt:formatDate value="${employee.empJd}" pattern="yyyy-MM-dd" /></span>
                </div>
                <div class="detail-item">
                    <span class="label">근무일수:</span>
                    <span class="value">${workDays}일</span>
                </div>
            </div>
            <div class="profile-actions">
                <a href="<c:url value='/user/employee/info' />" class="btn btn-primary btn-sm">인사정보 상세보기</a>
            </div>
        </div>

        <!-- 캘린더 -->
        <div class="calendar-container">
            <div class="dashboard-calendar">
      <h3>이번 달 일정</h3>
      <table class="calendar-table">
        <thead>
          <tr>
            <th class="sun">일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th class="sat">토</th>
          </tr>
        </thead>
        <tbody id="calendarBody"></tbody>
      </table>
      <div class="calendar-events" id="eventList"></div>
    </div>
  </div>
        </div>

        <!-- TO DO & MEMO -->
        <div class="dashboard-row">
            <div class="todo-list-container">
                <h4>TO DO LIST</h4>
                <c:forEach var="todo" items="${todoList}">
                    <div class="todo-item">
                        <input type="checkbox" id="todo_${todo.id}">
                        <label for="todo_${todo.id}">${todo.task}</label>
                    </div>
                </c:forEach>
            </div>

            <div class="memo-container">
                <h4>MEMO</h4>
                <textarea placeholder="메모를 입력하세요...">${memoContent}</textarea>
            </div>
        </div>
    </div>

<jsp:include page="../common/footer.jsp" />
<!-- 부트스트랩 및 일정 스크립트 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth();
  const calendarBody = document.getElementById("calendarBody");
  const eventList = document.getElementById("eventList");

  const firstDay = new Date(year, month, 1).getDay();
  const lastDate = new Date(year, month + 1, 0).getDate();

  let day = 1;
  let row = document.createElement("tr");
  for (let i = 0; i < firstDay; i++) row.appendChild(document.createElement("td"));
  for (let i = firstDay; i < 7; i++) row.appendChild(createDayCell(day++));
  calendarBody.appendChild(row);

  while (day <= lastDate) {
    row = document.createElement("tr");
    for (let i = 0; i < 7 && day <= lastDate; i++) {
      row.appendChild(createDayCell(day++));
    }
    calendarBody.appendChild(row);
  }

  function createDayCell(day) {
    const td = document.createElement("td");
    td.textContent = day;
    td.style.cursor = "pointer";
    td.addEventListener("click", () => {
      document.getElementById("modalDate").value = day;
      document.getElementById("calTitle").value = "";
      const modal = new bootstrap.Modal(document.getElementById("calendarModal"));
      modal.show();
    });
    return td;
  }

  function saveEvent() {
    const day = document.getElementById("modalDate").value;
    const title = document.getElementById("calTitle").value;
    if (!title.trim()) {
      alert("제목을 입력해주세요.");
      return;
    }

    const eventDiv = document.createElement("div");
    eventDiv.className = "event";
    eventDiv.innerHTML = `<span class="event-date">${day}일</span> <span class="event-title">${title}</span>`;
    eventList.appendChild(eventDiv);

    const modal = bootstrap.Modal.getInstance(document.getElementById("calendarModal"));
    modal.hide();
  }
</script>


<script src="<c:url value='/resources/js/script.js' />"></script>
<script src="<c:url value='/resources/js/session-timer.js' />"></script>
</body>
</html>
