[NODE]: <c:set lineNumber="1" offset="0" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="i18n" value="${I18n.getBundle('ayada_i18n', 'zh_cn')}">
[NODE]: </c:set>
[TEXT]: message1: 
[EXPR]: ${i18n.format(\"test.common.test4\", \"000\", \"111\", \"222\", \"333\")}
[TEXT]: \r\nmessage2: 
[EXPR]: ${i18n.message(\"test.common.test4\")}
[TEXT]: \r\n\r\n
[NODE]: <fmt:setBundle lineNumber="5" offset="7" length="2" tagClass="com.skin.ayada.jstl.fmt.SetBundleTag" tagFactory="com.skin.ayada.jstl.factory.SetBundleTagFactory" var="myI18n" basename="ayada_i18n">
[NODE]: </fmt:setBundle>
[TEXT]: \r\nmessage3: 
[EXPR]: ${myI18n.format(\"test.common.test4\", \"000\", \"111\", \"222\", \"333\")}
[TEXT]: \r\n\r\n
[NODE]: <fmt:bundle lineNumber="8" offset="12" length="6" tagClass="com.skin.ayada.jstl.fmt.BundleTag" tagFactory="com.skin.ayada.jstl.factory.BundleTagFactory" basename="ayada_i18n">
[TEXT]: \r\n    
[NODE]: <fmt:message lineNumber="9" offset="14" length="2" tagClass="com.skin.ayada.jstl.fmt.MessageTag" tagFactory="com.skin.ayada.jstl.factory.MessageTagFactory" key="test.common.test1">
[NODE]: </fmt:message>
[TEXT]: \r\n
[NODE]: </fmt:bundle>
[TEXT]: \r\n
[NODE]: <fmt:message lineNumber="11" offset="19" length="2" tagClass="com.skin.ayada.jstl.fmt.MessageTag" tagFactory="com.skin.ayada.jstl.factory.MessageTagFactory" key="test.common.test2" bundle="ayada_i18n">
[NODE]: </fmt:message>
[TEXT]: \r\n\r\n
[NODE]: <fmt:message lineNumber="13" offset="22" length="10" tagClass="com.skin.ayada.jstl.fmt.MessageTag" tagFactory="com.skin.ayada.jstl.factory.MessageTagFactory" key="test.common.test4" bundle="ayada_i18n">
[NODE]: <fmt:param lineNumber="14" offset="23" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="000">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="15" offset="25" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="111">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="16" offset="27" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="222">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="17" offset="29" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="333">
[NODE]: </fmt:param>
[NODE]: </fmt:message>
[TEXT]: \r\n\r\n
[NODE]: <fmt:message lineNumber="20" offset="33" length="14" tagClass="com.skin.ayada.jstl.fmt.MessageTag" tagFactory="com.skin.ayada.jstl.factory.MessageTagFactory" key="test.common.test4" bundle="ayada_i18n">
[NODE]: <fmt:param lineNumber="21" offset="34" length="3" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory">
[TEXT]: 000
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="22" offset="37" length="3" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory">
[TEXT]: 111
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="23" offset="40" length="3" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory">
[TEXT]: 222
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="24" offset="43" length="3" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory">
[TEXT]: 333
[NODE]: </fmt:param>
[NODE]: </fmt:message>
[TEXT]: \r\n\r\n
[NODE]: <fmt:message lineNumber="27" offset="48" length="10" tagClass="com.skin.ayada.jstl.fmt.MessageTag" tagFactory="com.skin.ayada.jstl.factory.MessageTagFactory" key="test.common.test4" bundle="ayada_i18n">
[NODE]: <fmt:param lineNumber="28" offset="49" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="aaa">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="29" offset="51" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="bbb">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="30" offset="53" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="ccc">
[NODE]: </fmt:param>
[NODE]: <fmt:param lineNumber="31" offset="55" length="2" tagClass="com.skin.ayada.jstl.fmt.FmtParamTag" tagFactory="com.skin.ayada.jstl.factory.FmtParamTagFactory" value="ddd">
[NODE]: </fmt:param>
[NODE]: </fmt:message>
[TEXT]: \r\n\r\n
[NODE]: <fmt:setLocale lineNumber="34" offset="59" length="2" tagClass="com.skin.ayada.jstl.fmt.SetLocaleTag" tagFactory="com.skin.ayada.jstl.factory.SetLocaleTagFactory" value="fr_fr">
[NODE]: </fmt:setLocale>
[TEXT]: \r\n
[NODE]: <fmt:formatNumber lineNumber="35" offset="62" length="2" tagClass="com.skin.ayada.jstl.fmt.NumberFormatTag" tagFactory="com.skin.ayada.jstl.factory.NumberFormatTagFactory" value="123456789.012">
[NODE]: </fmt:formatNumber>
[TEXT]: \r\n\r\n
[NODE]: <c:list lineNumber="37" offset="65" length="2" tagClass="com.skin.ayada.jstl.core.ListTag" tagFactory="com.skin.ayada.jstl.factory.ListTagFactory" name="userList">
[NODE]: </c:list>
[TEXT]: \r\n
[NODE]: <c:forEach lineNumber="38" offset="68" length="17" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" begin="1" end="5" varStatus="status">
[TEXT]: \r\n    
[NODE]: <c:map lineNumber="39" offset="70" length="6" tagClass="com.skin.ayada.jstl.core.MapTag" tagFactory="com.skin.ayada.jstl.factory.MapTagFactory" name="user">
[NODE]: <c:entry lineNumber="40" offset="71" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="userName" value="ayada${status.index}">
[NODE]: </c:entry>
[NODE]: <c:entry lineNumber="41" offset="73" length="2" tagClass="com.skin.ayada.jstl.core.AttributeTag" tagFactory="com.skin.ayada.jstl.factory.AttributeTagFactory" name="nickName" value="ayada${status.index}">
[NODE]: </c:entry>
[NODE]: </c:map>
[TEXT]: \r\n    
[NODE]: <c:execute lineNumber="43" offset="77" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${userList.add(user)}">
[NODE]: </c:execute>
[TEXT]:     <div>1 index: 
[EXPR]: ${status.index}
[TEXT]: : user.userName: 
[EXPR]: ${user.userName}
[TEXT]: </div>\r\n
[NODE]: </c:forEach>
[TEXT]: \r\n\r\n
[NODE]: <c:forEach lineNumber="47" offset="86" length="15" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" begin="1" end="5" step="2" var="user" varStatus="status">
[TEXT]: \r\n    <div>2 index: 
[EXPR]: ${status.index}
[TEXT]: , begin: 
[EXPR]: ${status.begin}
[TEXT]: , end: 
[EXPR]: ${status.end}
[TEXT]: , step: 
[EXPR]: ${status.step}
[TEXT]: , count: 
[EXPR]: ${status.count}
[TEXT]: , user.userName: 
[VARI]: ${user}
[TEXT]: </div>
[NODE]: </c:forEach>
