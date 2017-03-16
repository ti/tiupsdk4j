<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.tiup.sdk.oauth.Config" %>

<html>
<head>
  <title>Oauth Java SDK 请求演示</title>
</head>
<body>
<p>请阅读参考Oauth 2.0文档，以获得更明晰的体验。</p>
<p>
<h3> 请求示例</h3>
<a href="/exp"> 接口调用示例 /exp ，代码在：servlet.ExampleServlet下</a>
</p>

<p>
<h3> 路由列表</h3>
<div>请参考web.xml 配置响应的路由列表</div>
<p>/oauth/login 入口地址</p>
<p>/oauth/callback 回调地址，接口授权码</p>
</p>

<p>当前配置文件</p>

<pre>
      入口地址： <%=Config.getInstance().getLoginURL()%>
  </pre>

</body>
</html>
