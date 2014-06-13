/*
 * $RCSfile: ${className}.java,v $$
 * $Revision: 1.1 $
 * $Date: ${date} $
 *
 * Copyright (C) 2008 Skin, Inc. All rights reserved.
 *
 * This software is the proprietary information of Skin, Inc.
 * Use is subject to license terms.
 */
package ${packageName};

import java.io.IOException;

import com.skin.ayada.runtime.ExpressionContext;
import com.skin.ayada.runtime.JspWriter;
import com.skin.ayada.runtime.PageContext;
import com.skin.ayada.tagext.BodyContent;
import com.skin.ayada.tagext.BodyTag;
import com.skin.ayada.util.TagUtil;

public class ${className}
{
    public void execute(PageContext pageContext) throws IOException
    {
        JspWriter out = pageContext.getOut();
        ExpressionContext expressionContext = pageContext.getExpressionContext();
<c:forEach items="${nodeList}" var="node" varStatus="status">
    <c:choose>
        <c:when test="${node.nodeType == NodeType.TEXT}">
        /* TEXT: lineNumber: " + node.getLineNumber() + "*/");
        out.write("${CodeUtil.escape(node.toString())}");
        </c:when>
        <c:when test="${node.nodeType == NodeType.EXPRESSION}">
        /* EXPRESSION: lineNumber: " + node.getLineNumber() + "*/");
        out.write(expressionContext.getString("${CodeUtil.escape(node.toString())}"));
        </c:when>
        <c:when test="${node.length == 0}">throw new Exception("Exception at line #${node.lineNumber} ${NodeUtil.toString(node)} not match !")</c:when>
        <c:when test="${node.nodeName == 't:import'}"></c:when>
        <c:otherwise>
            <c:set var="parent" value="${node.parent}"/>
            <c:set var="tagName" value="${node.nodeName}"/>
            <c:set var="flagName" value="_tag_flag_${node.offset}"/>
            <c:set var="tagClassName" value="${tagLibrary.getTagClassName(tagName)}"/>
            <c:set var="tagInstanceName" value="_tag_instance_${node.offset}"/>
            <c:set var="parentInstanceName" value="_tag_instance_${parent.offset}"/>
            <c:if test="${util.isNull(parent)}"><c:set var="parentInstanceName" value="_tag_instance_undefined"/></c:if>
            <c:choose>
                <c:when test="${node.offset == status.index}">
                /* NODE START: lineNumber: ${node.lineNumber}, tagClassName: ${tagClassName}, tagInstanceName: ${tagInstanceName} */
                /* ${NodeUtil.toString(node)} */
                    <c:choose>
                        <c:when test="${tagName == 'c:if'}">
                            if(expressionContext.getBoolean(\"" + CodeUtil.escape(node.getAttribute("test")) + "\")){
                        </c:when>
                        <c:when test="${tagName == 'c:set'}">
                            pageContext.setAttribute("${node.getAttribute('var')}", TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute("value"))}"));
                        </c:when>
                        <c:when test="${tagName == 'c:out'}">
                            ${tagClassName} ${tagInstanceName} = new ${tagClassName}();
                            ${tagInstanceName}.setPageContext(pageContext);
                            ${tagInstanceName}.setValue(TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute('value'))}"));
                            <c:if test="${node.getAttribute('CodeUtil.escapeXml') == 'true'}">
                            ${tagInstanceName}.setCodeUtil.escapeXml(true);
                            </c:if>
                            ${tagInstanceName}.doStartTag();
                            this.doBody((BodyTag)${tagInstanceName}, pageContext);
                        </c:when>
                        <c:when test="${tagName == 'c:each' || tagName == 'c:forEach'}">
                            ${tagClassName} ${tagInstanceName} = new ${tagClassName}();
                            <c:if test="${util.isNotNull(parent)}">
                                ${tagInstanceName}.setParent(${parentInstanceName});
                            </c:if>
                            ${tagInstanceName}.setPageContext(pageContext);
                            ${tagInstanceName}.setItems(TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute('items'))}"));

                            <c:if test="${util.isNotNull(node.getAttribute('begin'))}">
                                ${tagInstanceName}.setBegin(TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute('begin'))}"));
                            </c:if>

                            <c:if test="${util.isNotNull(node.getAttribute('step'))}">
                                ${tagInstanceName}.setStep(TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute('step'))}"));
                            </c:if>

                            <c:if test="${util.isNotNull(node.getAttribute('end'))}">
                                ${tagInstanceName}.setEnd(TagUtil.evaluate(expressionContext, "${CodeUtil.escape(node.getAttribute('end'))}"));
                            </c:if>

                            <c:if test="${util.isNotNull(node.getAttribute('varStatus'))}">
                                pageContext.setAttribute("${node.getAttribute('end')}", ${tagInstanceName}.getLoopTagStatus());
                            </c:if>
                            ${tagInstanceName}.doStartTag();
                            while(${tagInstanceName}.hasNext()){
                                pageContext.setAttribute("${node.getAttribute('var')}", ${tagInstanceName}.next());
                        </c:when>
                        <c:when test="${tagName == 'c:choose'}">
                            ${tagClassName} ${tagInstanceName} = new ${tagClassName}();
                            <c:if test="${util.isNotNull(parent)}">
                                ${tagInstanceName}.setParent(${parentInstanceName});
                            </c:if>
                            ${tagInstanceName}.setPageContext(pageContext);
                        </c:when>
                        <c:when test="${tagName == 'c:when'}">
                            if(${parentInstanceName}.complete() == false && TagUtil.getBoolean(expressionContext, "${CodeUtil.escape(node.getAttribute('test'))}")){
                                ${parentInstanceName}.finish();
                        </c:when>
                        <c:when test="${tagName == 'c:otherwise'}">
                            ${parentInstanceName}.finish();
                        </c:when>
                        <c:otherwise>
                            ${tagClassName} ${tagInstanceName} = new ${tagClassName}();
                            <c:if test="${util.isNotNull(parent)}">
                                ${tagInstanceName}.setParent(${parentInstanceName});
                            </c:if>
                            ${tagInstanceName}.setPageContext(pageContext);

                            int ${flagName} = ${tagInstanceName}.doStartTag();

                            if(${flagName} == com.skin.ayada.tagext.Tag.SKIP_PAGE){
                                return;
                            }

                            if(${flagName} != com.skin.ayada.tagext.Tag.SKIP_BODY){
                                if(${flagName} != com.skin.ayada.tagext.Tag.EVAL_BODY_INCLUDE){
                                    if(${tagInstanceName} instanceof BodyTag)"){
                                        this.doBody((BodyTag)${tagInstanceName}, pageContext);
                                    }
                                }
                                while(true){
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${tagName == 'c:if'}">
                            } /* jsp.taglib.core.IfTag END */
                        </c:when>
                        <c:when test="${tagName == 'c:set'}">
                            /* jsp.taglib.core.SetTag END */
                        </c:when>
                        <c:when test="${tagName == 'c:out'}">
                            ${tagInstanceName}.doEndTag();");
                            pageContext.popBody();
                            /* jsp.taglib.core.OutTag END */");
                        </c:when>
                        <c:when test="${tagName == 'c:each' || tagName == 'c:forEach'}">
                            }
                            /* jsp.taglib.core.ForEachTag END */
                        </c:when>
                        <c:when test="${tagName == 'c:choose'}">
                            /* jsp.taglib.core.ChooseTag END */
                        </c:when>
                        <c:when test="${tagName == 'c:when'}">
                            }
                            /* jsp.taglib.core.WhenTag END */
                        </c:when>
                        <c:when test="${tagName == 'c:otherwise'}">
                            }
                            /* jsp.taglib.core.OtherwiseTag END */
                        </c:when>
                        <c:otherwise>
                            /* NODE END: lineNumber: ${node.lineNumber + node.offset}, tagClassName: ${tagClassName}, tagInstanceName: ${tagInstanceName} */
                                    ${flagName} = ${tagInstanceName}.doAfterBody();
                                    if(${flagName} != com.skin.ayada.tagext.IterationTag.EVAL_BODY_AGAIN){
                                        break;
                                    }
                                }
                                ${tagInstanceName}.doEndTag();

                                if(${tagInstanceName} instanceof com.skin.ayada.tagext.BodyTag){
                                    pageContext.popBody();
                                }
                            }
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</c:forEach>
    }

    public void doBody(BodyTag bodyTag, PageContext pageContext){
        BodyContent bodyContent = (BodyContent)(pageContext.pushBody());
        bodyTag.setBodyContent(bodyContent);
        bodyTag.doInitBody();
    }
}