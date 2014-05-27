[NODE]: <c:forEach lineNumber="1" offset="0" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="gmt_create,gmt_modified,game_id,market,channel,app_key,app_secret,os_require,download_url,size,xiaoer_create,xiaoer_modified ,attributes,target_revenue,options,identity,uri,detail_url,logo_url" var="name">
[TEXT]: \r\n            <isNotNull property=\"
[VARI]: ${name}
[TEXT]: \" prepend=\",\">\r\n                
[VARI]: ${name}
[TEXT]: =#
[VARI]: ${name}
[TEXT]: #\r\n            </isNotNull>\r\n
[NODE]: </c:forEach>
[TEXT]: \r\n
