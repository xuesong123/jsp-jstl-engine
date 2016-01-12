<t:comment>
这是一个cache的示例
cacheClient应该从控制层传递过来，此处为了测试方便，在页面上定义了一个cacheClient
cacheClient由应用定义，不需要继承任何类，只需要存在如下方法即可：
    public Object getCache(String key);
    public void setCache(String key, int expires, Object value);

c:cache的参数：
    cache - 可选，一个cache对象，必须存在 getCache和setCache方法。如果为空，从pageContext里面获取名称为cacheClient的对象。
    key - 可选，如果为空，则不执行缓存逻辑，直接输出内容。
    expires - 可选，如果为空或者小于1，则不执行缓存逻辑，直接输出内容。
</t:comment>

<h1>CacheTag Test</h1>
<c:bean name="cacheClient" className="test.com.skin.ayada.util.CacheClient"/>
<t:comment>
先缓存内容
</t:comment>

<div>
    <c:cache key="keyTest" expires="120">
    <h1>1. Hello World !</h1>
    </c:cache>

    <t:comment>
    输出缓存的内容
    </t:comment>

    <c:cache key="keyTest" expires="120">
        <h1>2. Hello World !</h1>
    </c:cache>
</div>
