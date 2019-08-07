<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c"%>
<%@ taglib uri="http://www.iknow.pt/jsp/jstl/iflow" prefix="if"%>
<%@ include file="inc/defs.jsp"%>

<%
    ProcessData mainProcessBackupProcessData = (ProcessData) session.getAttribute(Const.SESSION_PROCESS_POPUP_BACKUP + flowExecType);

    if (mainProcessBackupProcessData != null) {
        session.setAttribute(Const.SESSION_PROCESS + flowExecType, mainProcessBackupProcessData);

        session.removeAttribute(Const.SESSION_PROCESS_POPUP_BACKUP + flowExecType);
    }

    String title = messages.getString("flow_error.title");
    String msgKey = fdFormData.getParameter("msg_key");
    if(StringUtils.isBlank(msgKey)) {
        msgKey = "flow_error.error.runFlow";
    }
%>

<%@ include file="inc/process_top.jspf"%>
<div class="error_msg">
    <if:message string="<%=msgKey %>" />
</div>
<%@ include file="inc/process_bottom.jspf"%>