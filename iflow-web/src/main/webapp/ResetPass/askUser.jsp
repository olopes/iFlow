<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<%@ taglib uri="http://www.iknow.pt/jsp/jstl/iflow" prefix="if" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
  UserInfoInterface userInfo = (UserInfoInterface) session.getAttribute(Const.USER_INFO);
%>
<%@page import="pt.iflow.api.core.BeanFactory"%>
<%@page import="pt.iflow.api.utils.UserInfoInterface"%>
<%@page import="pt.iflow.api.utils.Const"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><if:message string="resetPassword.title" /></title>
		<link rel="stylesheet" href="Themes/<%=BeanFactory.getOrganizationThemeBean().getOrganizationTheme(userInfo).getThemeName() %>/css/iflow_main.css" type="text/css">
		<link rel="stylesheet" href="Themes/<%=BeanFactory.getOrganizationThemeBean().getOrganizationTheme(userInfo).getThemeName() %>/css/iflow_register.css" type="text/css">
		<link rel="shortcut icon" href="images/favicon.ico" />
		<!--[if IE]>
			<link rel="stylesheet" href="Themes/<%=BeanFactory.getOrganizationThemeBean().getOrganizationTheme(userInfo).getThemeName() %>/css/iflow_ie.css" type="text/css">
		<![endif]-->
	</head>
	<body class="rp_body">
		<%--<div class="rp_fixed_width">
			<if:titlebubble message="register.title" floatpos="left" transparent="false"/>
			<if:titlebubble message="register.title.step.3" floatpos="right" transparent="true" marginpos="left"/>
			<if:titlebubble message="register.title.step.2" floatpos="right" transparent="true" marginpos="left"/>
			<if:titlebubble message="register.title.step.1" floatpos="right" transparent="false"/>
		</div>--%>
		<div class="rc_box">
			<div class="rc_header"></div> 
			<div class="rc_content">
				<form name="fomulario" id="formulario" action="<%=response.encodeURL("resetPassword") %>" method="post">
					<fieldset class="rp_background">
						<legend style="border: none;font-size: 2rem;"><if:message string="resetPassword.title" /></legend>
						<%--<div class="info_msg rp_background" style="text-align:left;">
							<if:message string="register.intro.organization" />
						</div>--%>
						<c:if test="${not empty error_msg}">
							<div class="error_msg rp_background">
								<c:out value="${error_msg}" escapeXml="false"/>
							</div>
						</c:if>
					
						<ol>
							<if:formInput type="text" name="username" value="" labelkey="resetPassword.label.username" edit="true" required="true" />
							<if:formInput type="challenge" name="challenge" value="" label="Challenge" edit="true" required="true" />
						</ol>
					</fieldset>
					<fieldset class="submit rp_background centrarBotoes">
						<input class="regular_button_01" type="submit" name="cancel" value="<if:message string="button.cancel"/>"/>
						<%--<input class="regular_button_01" type="reset" name="clear" value="<if:message string="button.clear"/>"/>--%>
						<input class="regular_button_01" type="submit" name="add" value="<if:message string="button.next"/>"/>
					</fieldset>
				</form>
			</div> 
		</div>
	<div class="lp_footer_nav">
	  <a class="lp_top_nav_link" style="color:#5D7891;" href="http://www.iknow.pt"><if:message string="iflow.copyright"/></a>
    </div>
		
	</body>
</html>