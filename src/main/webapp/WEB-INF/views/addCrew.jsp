<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TVPSS - Add Crew Member</title>
<link
	href="${pageContext.request.contextPath}/resources/css/SideBarStyles.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
/* Keeping the same styles from the original page */
body {
	background-color: #f8f9fa;
}

.main-container {
	display: flex;
	min-height: 100vh;
}

.main-content {
	flex: 1;
	padding: 20px 40px;
	background-color: white;
	margin: 5px;
}

.user-section {
	display: flex;
	align-items: center;
	gap: 10px;
	position: absolute;
	top: 20px;
	right: 40px;
}

.user-avatar {
	width: 40px;
	height: 40px;
	border-radius: 50%;
	background-color: #ddd;
}

.card {
	border: none;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-top: 2rem;
}

.form-container {
	max-width: 600px;
	margin: 0 auto;
	padding: 2rem;
}

.form-group {
	margin-bottom: 1.5rem;
}

.form-label {
	font-weight: 500;
	color: #333;
	margin-bottom: 0.5rem;
}

.form-select, .form-control {
	border-radius: 8px;
	border: 1px solid #ddd;
	padding: 0.75rem;
}

.submit-btn {
	background-color: #9c27b0;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 8px;
	font-weight: 500;
	cursor: pointer;
	transition: background-color 0.3s ease;
}

.submit-btn:hover {
	background-color: #7b1fa2;
}

.back-btn {
	color: #6c757d;
	text-decoration: none;
	margin-right: 1rem;
}

.back-btn:hover {
	color: #343a40;
}
</style>
</head>
<body>
	<div class="main-container">
		<!-- Sidebar -->
		<jsp:include page="/WEB-INF/views/sidebar/sidebarAdmin.jsp" />

		<!-- Main Content -->
		<div class="main-content">
			<!-- User Section -->
			<div class="user-section">
				<div class="user-avatar"></div>
				<span>Katie Pena (Admin)</span>
			</div>

			<h3 class="mb-4">Add New Crew Member</h3>

			<div class="card">
				<div class="form-container">
					<form
						action="${pageContext.request.contextPath}/manage-school/add-crew/${schoolId}"
						method="POST">
						<div class="form-group">
							<label class="form-label">Select Student</label> <select
								name="studentId" class="form-select" required>
								<option value="">Choose a student...</option>
								<c:forEach items="${students}" var="student">
									<option value="${student.id}">${student.name}- Year
										${student.year}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label class="form-label">Role</label> <input type="text"
								name="role" class="form-control" required>
						</div>

						<div class="d-flex justify-content-end mt-4">
							<input type="hidden" id="csrf-token"
								name="${_csrf.parameterName}" value="${_csrf.token}" /> <a
								href="${pageContext.request.contextPath}/manage-school/crew-page/${schoolId}"
								class="back-btn">Cancel</a>
							<button type="submit" class="submit-btn">Add Crew Member</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>