<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/admin-sidebar.jsp">
    <jsp:param name="menu" value="dashboard" />
</jsp:include>

<style>
.admin-dashboard h2 {
    margin-bottom: 25px;
    font-weight: bold;
}
.dashboard-stats {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
}
.stat-card {
    flex: 1;
    background-color: #fff;
    border-radius: 16px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    display: flex;
    align-items: center;
}
.stat-icon {
    font-size: 28px;
    color: #4e73df;
    margin-right: 15px;
}
.stat-info h3 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
}
.stat-value {
    font-size: 24px;
    font-weight: bold;
}
.stat-change {
    font-size: 14px;
    color: #888;
}
.stat-change.positive { color: green; }
.stat-change.negative { color: red; }

.dashboard-row {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
}
.dashboard-chart, .dashboard-calendar, .recent-notices, .pending-approvals {
    flex: 1;
    background: #fff;
    padding: 20px;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.chart-container .chart-bar {
    display: flex;
    margin-bottom: 10px;
    background: #f5f5f5;
    border-radius: 8px;
    overflow: hidden;
}
.chart-bar .bar-label {
    padding: 8px;
    width: 120px;
    background: #4e73df;
    color: #fff;
}
.chart-bar .bar-value {
    padding: 8px;
    background: #dfe6fd;
    color: #333;
}

.calendar-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 15px;
}
.calendar-table th, .calendar-table td {
    text-align: center;
    padding: 10px;
}
.calendar-table .has-event {
    background-color: #fffbdd;
    border: 1px solid #ffdd57;
}
.calendar-events .event {
    font-size: 14px;
    margin-bottom: 8px;
}
.calendar-events .event-date {
    font-weight: bold;
    margin-right: 8px;
}

.data-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}
.data-table th, .data-table td {
    padding: 8px;
    border-bottom: 1px solid #e0e0e0;
    text-align: left;
}
.data-table a {
    text-decoration: none;
    color: #4e73df;
}
.view-more {
    text-align: right;
    margin-top: 10px;
}
.btn.btn-outline {
    border: 1px solid #4e73df;
    color: #4e73df;
    padding: 5px 12px;
    border-radius: 6px;
    background: white;
    transition: 0.2s;
}
.btn.btn-outline:hover {
    background: #4e73df;
    color: white;
}

.chart-bar {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
    background-color: #e9ecef;
    border-radius: 6px;
    overflow: hidden;
}
.bar-label {
    width: 120px;
    padding: 6px 10px;
    background-color: #6c757d;
    color: white;
    font-weight: 500;
}
.bar-value {
    background-color: #8faee0;  
    color: white;
    padding: 5px;
    border-radius: 4px;
    text-align: right;
}


}


</style>

<div class="content">
    <div class="admin-dashboard">
        <h2>관리자 대시보드</h2>

        <!-- 통계 카드 -->
        <div class="dashboard-stats">
            <!-- 각 stat-card 반복 -->
            <div class="stat-card">
			    <div class="stat-icon"><i class="fas fa-users"></i></div>
			    <div class="stat-info">
			        <h3>총 직원 수</h3>
			        <p class="stat-value">${totalEmployees}</p>
			        <p class="stat-change positive">+${newEmployees} (이번 달)</p>
			    </div>
			</div>

            

            <div class="stat-card">
			    <div class="stat-icon"><i class="fas fa-file-alt"></i></div>
			    <div class="stat-info">
			        <h3>결재 대기</h3>
			        <p class="stat-value">${pendingCount}</p>
			        <p class="stat-change negative">미처리 문서</p>
			    </div>
			</div>

            
        </div>

        <!-- 차트 + 달력 -->
        <div class="dashboard-row">
		    <div class="dashboard-chart" style="flex: 1;">
		        <!-- 부서별 인원 현황 -->
		        <h3>부서별 인원 현황</h3>
		        <div class="chart-container">
		            <c:forEach var="dept" items="${depCounts}">
					    <c:set var="count" value="${dept.empCount}" />
					    
					    <%-- 전체 사원 수 기준으로 width 계산 --%>
					    <c:set var="widthPercentRaw" value="${(count * 100) / totalEmployees}" />
					    
					    <%-- 최소 10% 보장 --%>
					    <c:set var="widthPercent" value="${widthPercentRaw < 10 && count > 0 ? 10 : widthPercentRaw}" />
					
					    <div class="chart-bar">
					        <div class="bar-label">${dept.depName}</div>
					        <div class="bar-value" style="width: ${widthPercent}%;">
					            ${count}
					        </div>
					    </div>
					</c:forEach>


		        </div>
		    </div>
			
			<!-- 최근 공지/결재 -->
		    <div class="recent-notices">
			        <h3>최근 공지사항</h3>
			        <table class="data-table">
			            <thead>
			                <tr>
			                    <th>제목</th>
			                    <th>작성자</th>
			                    <th>작성일</th>
			                    <th>조회수</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:forEach var="notice" items="${noticeList}">
			                    <tr>
			                        <td>
			                            <a href="<c:url value='/admin/notice/detail/${notice.notId}' />">
			                                ${notice.notTi}
			                            </a>
			                        </td>
			                        <td>${notice.notRegister}</td>
			                        <td><fmt:formatDate value="${notice.notWd}" pattern="yyyy-MM-dd" /></td>
			                        <td>${notice.viewCount}</td>
			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			        <div class="view-more">
			            <a href="<c:url value='/admin/notice/manage' />" class="btn btn-outline">더보기</a>
			        </div>
			    </div>
		</div>


       
        
    </div>
</div>

<jsp:include page="../common/footer.jsp" />

<script src="<c:url value='/resources/js/script.js' />"></script>
<script src="<c:url value='/resources/js/session-timer.js' />"></script>
</body>
</html>
