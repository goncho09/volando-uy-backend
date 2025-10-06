<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${appName}</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 600px;
            width: 100%;
            text-align: center;
        }
        
        h1 {
            color: #667eea;
            margin-bottom: 20px;
            font-size: 2.5em;
        }
        
        .message {
            color: #555;
            font-size: 1.2em;
            margin-bottom: 30px;
        }
        
        .nav-links {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }
        
        .nav-links a {
            background: #667eea;
            color: white;
            text-decoration: none;
            padding: 12px 30px;
            border-radius: 25px;
            transition: all 0.3s ease;
            font-weight: 600;
        }
        
        .nav-links a:hover {
            background: #764ba2;
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        
        .info {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #eee;
            color: #888;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>✈️ ${appName}</h1>
        <p class="message">${message}</p>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/vuelos">View Flights</a>
            <a href="${pageContext.request.contextPath}/home">Home</a>
        </div>
        
        <div class="info">
            <p>A Java backend application with JSP</p>
            <p>Built with Servlets, JSP, and JSTL</p>
        </div>
    </div>
</body>
</html>
