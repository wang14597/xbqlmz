### Gateway服务

#### 通过如下2种方式进行路由拦截的权限验证


1. 通过 Spring security [SecurityConfig](src%2Fmain%2Fjava%2Fcom%2Fwwlei%2Fgateway%2Fsecurity%2FSecurityConfig.java)，进行权限验证

2. 通过全局过滤器([TokenAuthenticationFilter](src%2Fmain%2Fjava%2Fcom%2Fwwlei%2Fgateway%2Ffilter%2FTokenAuthenticationFilter.java))拦截请求，进行权限验证