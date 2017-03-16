# TiUP Oauth API SDK For Java

## 说明

exmaple/jsp 目录：jsp Servlet 示例
exmaple/android 目录： android 示例

> 编译时请确保您的网络通畅，可能需要翻墙才能编译成功。


src 目录： API SDK 源码

## 特性：

1. 支持 clientCredentials , auth code Credentials, passwordCredentialsToken 全功能Oauth模式
2. 支持 access_token 自动刷新

## 使用方法

* 1. Jsp服务器端

参考web.xml 配置路由，参考 oauthconfig.properties 填写配置参数，参考； ExampleServlet.java 接入代码

### 用户授权
```java
Oauth2 client = Oauth2Utils.userServletClient(request);
GenericResponse<UserInfo> resp = oauth2.getUserInfo();
```

#### 企业应用授权

```java
Oauth2 client =  Oauth2Utils.clientCredentialsClient(Config.getInstance())
GenericResponse<SomeObject> resp = oauth.get("/apis/v1/object",SomeObject.class)
```
 
* 2. Android 手机端

```java
 public static void Main() throws IOException {
         //接入配置
         Config  config = Config.getInstance();
         config.setClientID("clientId");
         config.setClientSecret("clientSecret");
         //获取token，从配置文件获取
         Token tokenSource = config.passwordCredentialsToken("username","password",null);
         Oauth2 oauth2 = Oauth2Utils.authTokenClient(config,tokenSource, new Oauth2.TokenChangeListener() {
 
             @Override
             public void onRefresh(Token orgToken, Token newToken) {
                 //更新你的token
             }
 
             @Override
             public void onRevoke(Token token) {
                 //删除你的token
             }
         });
         oauth2.setOnTokenUnauthorizedListener(new Oauth2.TokenUnauthorizedListener() {
             @Override
             public void onUnauthorized() throws IOException {
                 //退出重新登录
             }
         });
         
         //API 请求
         GenericResponse<UserInfo> resp = oauth2.getUserInfo();
         if (resp.isSucceed()) {
             System.out.println("用户姓名：" + resp.getData().getName());
         } else {
             System.out.println("请求出错：" + resp.getBody());
         }
         
     }
```


### 依赖
```bash
gson-2.8.0.jar
okhttp-3.6.0.jar
okio-1.11.0.jar
```