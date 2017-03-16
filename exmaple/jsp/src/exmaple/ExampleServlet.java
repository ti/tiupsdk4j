package exmaple;


import cn.tiup.sdk.model.GenericResponse;
import cn.tiup.sdk.model.UserInfo;
import cn.tiup.sdk.oauth.Oauth2;
import cn.tiup.sdk.util.Oauth2Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 示例代码，代码包含获取用户ID，和接口调用部分；
 *
 * 基本请求演示：
 * GET 请求：
 * GenericResponse<UserInfo> userInfo = client.get("/apis/oauth2/v1/userinfo",UserInfo.class);
 * POST 请求：
 * 1. GenericResponse<UserInfo> userInfoGenericResponse = client.postForm("/apis/oauth2/v1/userinfo",new HashMap<String, String>(),UserInfo.class);
 * 2, GenericResponse<UserInfo> userInfoGenericResponse = client.postJson("/apis/oauth2/v1/userinfo","{\"name\":\"exp\"}",UserInfo.class);
 * 其他高级请求：
 * GenericResponse<UserInfo> userInfo = client.execute("PATCH", "/apis/oauth2/v1/userinfo", null, UserInfo.class);
 * Response res = client.response("DELETE", "/apis/oauth2/v1/userinfo", null);
 */

public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Oauth2 client = Oauth2Utils.userServletClient(request);
        if (client == null) {
            //没有获取到 client 重新获取授权
            Oauth2Utils.reAuthUserServlet(request,response);
            return;
        }
        //判断用户是否是登录态（可选）
        //获取用户ID
        String userId = client.getToken().getUserId();

        //普通接口请求示例
        GenericResponse<UserInfo> userInfo = client.get("/apis/oauth2/v1/userinfo",UserInfo.class);
        if(userInfo.getStatus() == 401) {
            //如果返回401，则表示 TOKEN过期， 可以重新请求TOKEN
            Oauth2Utils.reAuthUserServlet(request,response);
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        String outTemplate = "<!DOCTYPE html><head><title>%s</title></head><body><div>%s</div></body></html>";
        response.getWriter().print(String.format(outTemplate, "接口演示", "获取用户信息：<br>UID: " + userInfo.getData().getUserId() + "<br>姓名: " + userInfo.getData().getName()));
    }

}
