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
                <div class="stat-icon"><i class="fas fa-user-clock"></i></div>
                <div class="stat-info">
                    <h3>오늘 출근</h3>
                    <p class="stat-value">118</p>
                    <p class="stat-change">출근율: 94.4%</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon"><i class="fas fa-file-alt"></i></div>
                <div class="stat-info">
                    <h3>결재 대기</h3>
                    <p class="stat-value">12</p>
                    <p class="stat-change negative">+5 (어제 대비)</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon"><i class="fas fa-calendar-alt"></i></div>
                <div class="stat-info">
                    <h3>이번 달 휴가</h3>
                    <p class="stat-value">28</p>
                    <p class="stat-change">승인 대기: 5</p>
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
		
		    <div class="dashboard-calendar" style="flex: 1;">
		        <!-- 이번 달 일정 -->
		        <h3>이번 달 일정</h3>
		        <table class="calendar-table">
		            <thead>
                        <tr><th class="sun">일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th class="sat">토</th></tr>
                    </thead>
                    <tbody>
                        <tr><td></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td></tr>
                        <tr><td>7</td><td>8</td><td class="has-event">9</td><td>10</td><td>11</td><td>12</td><td>13</td></tr>
                        <tr><td>14</td><td>15</td><td>16</td><td class="has-event">17</td><td>18</td><td>19</td><td>20</td></tr>
                        <tr><td>21</td><td>22</td><td>23</td><td>24</td><td class="has-event">25</td><td>26</td><td>27</td></tr>
                        <tr><td>28</td><td>29</td><td>30</td><td>31</td><td></td><td></td><td></td></tr>
                    </tbody>
		        </table>
		        <div class="calendar-events">
		            <div class="event"><span class="event-date">9일</span><span class="event-title">경영회의</span></div>
		            <div class="event"><span class="event-date">17일</span><span class="event-title">신입사원 교육</span></div>
		            <div class="event"><span class="event-date">25일</span><span class="event-title">부서장 회의</span></div>
		        </div>
		    </div>
		</div>


        <!-- 최근 공지/결재 -->
        <div class="dashboard-row">
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
			


            <div class="pending-approvals">
                <h3>결재 대기 문서</h3>
                <table class="data-table">
                    <thead><tr><th>문서번호</th><th>제목</th><th>신청자</th><th>신청일</th></tr></thead>
                    <tbody>
                        <tr><td>AP-2025-042</td><td><a href="#">휴가 신청서</a></td><td>홍길동</td><td>2025-03-27</td></tr>
                        <tr><td>AP-2025-041</td><td><a href="#">지출 결의서</a></td><td>김영희</td><td>2025-03-26</td></tr>
                        <tr><td>AP-2025-040</td><td><a href="#">업무 보고서</a></td><td>이철수</td><td>2025-03-26</td></tr>
                    </tbody>
                </table>
                <div class="view-more">
                    <a href="<c:url value='/admin/approval/manage' />" class="btn btn-outline">더보기</a>
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
