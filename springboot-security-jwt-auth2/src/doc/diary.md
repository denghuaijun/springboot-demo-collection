#SpirngSecurity整合JWT学习
https://jwt.io/   可以通过官网来进行jwt生成的token转换。
## JWT组成
1 . jwt的格式，是由三部分组成，然后以"."隔开的形式存在，具体可以参考jwt官网Debugger示例：
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
2. 以点隔开三份，从左往右分别是，header，payload，sign
   1.  header:主要存放生成jwt的签名算法，进行base64编码生成
   2.  payload： 主要用来存放的服务端与客户端之间的交互数据。然后进行base64编码生成
   3.  sign：签名是由payload + 对应的签名key或者盐值通过MD5加密的来，主要用来验签及防止抓包
   
## Jwt如何注销账号
1. 忘记密码/注销，直接清理客户端缓存，比如清楚cookie
2. 权限发生变化的情况，管理员通知用户重新登陆，或者联系管理远修改权限
3. jwt有效期不能设置太长

#oauth2学习
Oauth2不是一门技术，而是一种开放授权协议。为了保证接口的安全性
## oauth2协议的四种授权方式
    1.    授权码模式，通过授权码获取对应的令牌也就是accessToken   -----比较安全
    2.    用户名密码模式，需要输入用户名和密码------这是不安全的
    3.    简易模式
    4.    客户端模式
     
    
## oauth2协议的角色划分
1. client ：调用者，要调用开发API的合作伙伴
2. Authentication Server ：认证授权中心，主要负责 客户端调用返回授权码及对应的accessToken
3. Resource Server: 进行验证accessToken