<%@ include file="/include-internal.jsp"%>
<%@ page import="disableAllAgents.Constants" %>
<c:set var="QUEUE_ACTIONS_URL" value="<%=Constants.WEB.AGENTS_ACTIONS_URL%>"/>
<c:set var="AGENTS_STATUS_PARAMETER_NAME" value="<%=Constants.WEB.AGENTS_STATUS_PARAMETER_NAME%>"/>
<script type="text/javascript">
  var setAgentsStatus = function(enable) {
      var params = {
        "${AGENTS_STATUS_PARAMETER_NAME}": enable
      };
      var url = window['base_uri'] + "${QUEUE_ACTIONS_URL}";
      BS.ajaxRequest(url, {
        parameters: params,
        onSuccess: function() {
          window.location.reload();
        }
      });
  };
  $j('.quickLinks').prepend('<a href="#" class="quickLinksItem" onclick="setAgentsStatus(true)">Enable All Agents</a>');
  $j('.quickLinks').prepend('<a href="#" class="quickLinksItem" onclick="setAgentsStatus(false)">Disable All Agents</a>');
</script>
