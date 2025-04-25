<form action="<c:url value='/admin/work-update' />" method="post">
    <input type="hidden" name="empId" value="${work.empId}" />
    <input type="hidden" name="workDate" value="${work.workDate}" />

    <label>근무 시간 (시간):</label>
    <input type="number" name="workHours" value="${work.workHours}" />

    <label>야근 시간:</label>
    <input type="number" name="nightWorkHour" value="${work.nightWorkHour}" />

    <label>휴일 근무 시간:</label>
    <input type="number" name="holidayWorkHour" value="${work.holidayWorkHour}" />

    <label>근무 상태:</label>
    <select name="attendanceStatus">
        <option value="출근" ${work.attendanceStatus == '출근' ? 'selected' : ''}>출근</option>
        <option value="지각" ${work.attendanceStatus == '지각' ? 'selected' : ''}>지각</option>
        <option value="결근" ${work.attendanceStatus == '결근' ? 'selected' : ''}>결근</option>
        <option value="출장" ${work.attendanceStatus == '출장' ? 'selected' : ''}>출장</option>
        <option value="조퇴" ${work.attendanceStatus == '조퇴' ? 'selected' : ''}>조퇴</option>
    </select>

    <label>휴가 일수:</label>
    <input type="number" name="vacationDays" value="${work.vacationDays}" />

    <br><br>
    <button type="submit">수정 완료</button>
</form>

<!-- 삭제 버튼 -->
<form action="<c:url value='/admin/work-delete' />" method="post" style="margin-top:10px;">
    <input type="hidden" name="empId" value="${work.empId}" />
    <input type="hidden" name="workDate" value="${work.workDate}" />
    <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')">삭제하기</button>
</form>
