<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flights - Volando UY</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 40px 20px;
        }
        
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        
        h1 {
            color: white;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.5em;
        }
        
        .vuelos-table {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        thead {
            background: #667eea;
            color: white;
        }
        
        th {
            padding: 20px;
            text-align: left;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.9em;
            letter-spacing: 1px;
        }
        
        td {
            padding: 18px 20px;
            border-bottom: 1px solid #f0f0f0;
        }
        
        tbody tr:hover {
            background: #f8f9ff;
        }
        
        tbody tr:last-child td {
            border-bottom: none;
        }
        
        .status {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
        }
        
        .status-ontime {
            background: #d4edda;
            color: #155724;
        }
        
        .status-delayed {
            background: #fff3cd;
            color: #856404;
        }
        
        .status-boarding {
            background: #d1ecf1;
            color: #0c5460;
        }
        
        .back-link {
            display: inline-block;
            margin-top: 30px;
            background: white;
            color: #667eea;
            text-decoration: none;
            padding: 12px 30px;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        
        .back-link:hover {
            background: #f8f9ff;
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(255, 255, 255, 0.3);
        }
        
        .header-section {
            text-align: center;
            margin-bottom: 20px;
        }
        
        @media (max-width: 768px) {
            table {
                font-size: 0.9em;
            }
            
            th, td {
                padding: 12px 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-section">
            <h1>✈️ Flight Information</h1>
        </div>
        
        <div class="vuelos-table">
            <table>
                <thead>
                    <tr>
                        <th>Flight Number</th>
                        <th>Origin</th>
                        <th>Destination</th>
                        <th>Departure</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="flight" items="${vuelos}">
                        <tr>
                            <td><strong>${flight.flightNumber}</strong></td>
                            <td>${flight.origin}</td>
                            <td>${flight.destination}</td>
                            <td>${flight.departureTime}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${flight.status == 'On Time'}">
                                        <span class="status status-ontime">${flight.status}</span>
                                    </c:when>
                                    <c:when test="${flight.status == 'Delayed'}">
                                        <span class="status status-delayed">${flight.status}</span>
                                    </c:when>
                                    <c:when test="${flight.status == 'Boarding'}">
                                        <span class="status status-boarding">${flight.status}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status">${flight.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="header-section">
            <a href="${pageContext.request.contextPath}/home" class="back-link">← Back to Home</a>
        </div>
    </div>
</body>
</html>
