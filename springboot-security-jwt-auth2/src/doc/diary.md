#SpirngSecurity整合JWT学习
https://jwt.io/   可以通过官网来进行jwt生成的token转换。
## JWT组成
1 . jwt的格式，是由三部分组成，然后以"."隔开的形式存在，具体可以参考jwt官网Debugger示例：
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
2. 以点隔开三份，从左往右分别是，header，payload，sign
   1.  header:主要存放生成jwt的签名算法，进行base64编码生成
   2.  payload： 主要用来存放的服务端与客户端之间的交互数据。然后进行base64编码生成
   3.  sign：签名是由payload + 对应的签名key或者盐值通过MD5加密的来，主要用来验签及防止抓包