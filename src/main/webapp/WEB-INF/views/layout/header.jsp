<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--세션에 접근하는방법--%>
<sec:authorize access="isAuthenticated()">
<%--	property에 써논 값으로 세션에 접근하면 된다는 뜻--%>
	<%--이렇게 설정하면 매번 컨트롤러로 세션 model을 안남겨줘도 됨 단 넘겨받는값은
	principalDetails이기때문에 .user를 붙여줘야함--%>
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Photogram</title>

	<!-- 제이쿼리 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<!-- Style -->
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="/css/story.css">
	<link rel="stylesheet" href="/css/popular.css">
	<link rel="stylesheet" href="/css/profile.css">
	<link rel="stylesheet" href="/css/upload.css">
	<link rel="stylesheet" href="/css/update.css">
	<link rel="shortcut icon" href="/images/insta.svg">
	
	<!-- Fontawesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
</head>

	<body>

	<!-- principalId 담아두는 곳 -->
	<input type = "hidden" id="principalId" value="${principal.user.id}" />
	
	<header class="header">
		<div class="container">
			<a href="/" class="logo">
				<img src="/images/logo.jpg" alt="">
			</a>
			<nav class="navi">
				<ul class="navi-list">
					<li class="navi-item"><a href="/">
							<i class="fas fa-home"></i>
						</a></li>
					<li class="navi-item"><a href="/image/popular">
							<i class="far fa-compass"></i>
						</a></li>
					<li class="navi-item"><a href="/user/1">
							<i class="far fa-user"></i>
						</a></li>
				</ul>
			</nav>
		</div>
	</header>


