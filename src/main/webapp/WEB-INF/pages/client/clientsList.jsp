<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_MX"/>

<section id="clientes">
    <div class="container">
        <div class="row">
            <div class="col-md-9">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">Clients List</h4>
                    </div>
                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Balance</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Iterating over each element of the list -->
                            <c:forEach var="client" items="${clients}" varStatus="status" >
                                <tr>
                                    <td>${status.count}</td>
                                    <td>${client.name} ${client.lastName}</td>
                                    <td> <fmt:formatNumber value="${client.balance}" type="currency"/> </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ControllerServlet?action=edit&clientID=${client.clientID}"
                                           class="btn btn-secondary">
                                            <i class="fas fa-angle-double-right"></i> Edit
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!--Cards for total balances -->
            <div class="col-md-3">
                <div class="card text-center bg-danger text-white mb-3">
                    <div class="card-body">
                        <h3>Total Balance</h3>
                        <h4 class="display-4">
                            <fmt:formatNumber value="${totalBalance}" type="currency" />
                        </h4>
                    </div>
                </div>

                <div class="card text-center bg-success text-white mb-3">
                    <div class="card-body">
                        <h3>Clients registered</h3>
                        <h4 class="display-4">
                            <i class="fas fa-users"></i> ${totalClients}
                        </h4>
                    </div>
                </div>        
            </div>
            <!--End of cards for totals-->
        </div>
    </div>
</section>

<!-- MODAL client -->
<jsp:include page="/WEB-INF/pages/client/newClient.jsp"/>
                        