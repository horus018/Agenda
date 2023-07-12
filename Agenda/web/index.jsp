<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="listaTarefas" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="msgErro" scope="session" class="java.lang.String"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tarefas</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.tarefaEdicao}">
            <h1>Nova Tarefa</h1>
            <form action="tarefas" method="POST">
                O que: <input type="text" name="descr" size="80"/><br/>
                Quem: <input type="text" name="quem" size="30"/><br/>
                Até: <input type="text" name="prazo" size="15"/><br/>
                <input type="submit" value="Criar tarefa"/>
            </form>
            <c:if test="${not empty msgErro}">
                <p style="color: red">${msgErro}</p>
            </c:if>
            <c:if test="${empty listaTarefas}">
                <h2>Não há tarefas cadastradas</h2>
            </c:if>
            <c:if test="${not empty listaTarefas}">
                <table>
                    <tr>
                        <th style="min-width: 200px;">O que fazer</th>
                        <th style="min-width: 150px;">Responsável</th>
                        <th style="min-width: 40px;">Prazo</th>
                        <th style="min-width: 30px;">Feito</th>
                        <th style="min-width: 80px;">Ação</th>
                    </tr>
                    <c:set var="pos" value="0" scope="request"/>
                    <c:forEach items="${listaTarefas}" var="trf">
                        <tr>
                            <td>${trf.descricao}</td>
                            <td>${trf.responsavel}</td>
                            <td><fmt:formatDate value="${trf.prazo}"
                                            pattern="dd/MM/yyyy"/></td>
                            <td>${tfr.terminada}</td>
                            <td style="text-align: center;">
                                <c:if test="${not trf.terminada}">
                                    <form action="tarefas" method="POST">
                                        <input type="hidden" name="acao" value="confirmar"/>
                                        <input type="hidden" name="posicao" value="${pos}"/>
                                        <input type="submit" value="Confirmar"/>
                                    </form>
                                </c:if>
                                <c:if test="${trf.terminada}">
                                    <!-- esse codigo ai em baixo é isso: ✔-->
                                    &#10004;
                                </c:if>
                            </td>
                            <td>
                                <form action="tarefas" method="POST">
                                    <input type="hidden" name="acao" value="excluir"/>
                                    <input type="hidden" name="posicao" value="${pos}"/>
                                    <input type="submit" value="Excluir"/>
                                </form>
                            </td>
                            <td>
                                <form action="tarefas" method="POST">
                                    <input type="hidden" name="acao" value="editar"/>
                                    <input type="hidden" name="posicao" value="${pos}"/>
                                    <input type="submit" value="Editar"/>
                                </form>
                            </td>
                        </tr>
                        <c:set var="pos" value="${pos+1}"/>
                    </c:forEach>
                </table>
            </c:if>
        </c:if>


        <c:if test="${not empty sessionScope.tarefaEdicao}">
            <h1>Editar tarefa</h1>
            <form action="tarefas" method="POST">
                O que: <input type="text" name="descr" size="80" value="${tarefaEdicao.descricao}"/><br/>
                Quem: <input type="text" name="quem" size="30" value="${tarefaEdicao.responsavel}"/><br/>
                Até: <input type="text" name="prazo" size="15" value='<fmt:formatDate value="${tarefaEdicao.prazo}" pattern="dd/MM/yyyy"/>'/><br/>
                <input type="submit" value="Alterar tarefa"/>
                <input type="submit" name="cancelar" value="Cancelar"/>
            </form>
            <c:if test="${not empty msgErro}">
                <p style="color: red">${msgErro}</p>
            </c:if>    
        </c:if>
        <c:remove var="msgErro" scope="session"/>
    </body>

</html>
