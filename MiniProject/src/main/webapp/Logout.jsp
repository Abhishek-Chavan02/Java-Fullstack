<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="pack.Product" %>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout</title>
    <link rel="stylesheet" href="Style.css">
    <style>
        .product-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
}

.product-card {
    flex: 1 1 calc(33% - 20px);
    box-sizing: border-box;
    margin-bottom: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    background-color: #fff;
    transition: transform 0.3s, box-shadow 0.3s;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.product-card img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
}

.title{
align-item:center;
margin-bottom:10px;
}
    </style>
</head>
<body>
    <div class="container">
        <nav class="nav-list">
            <div class="head">
                <h1>Amazon</h1>
            </div>
            <ul class="list">
                <li class="nav-link log"><a href="#Product">Home</a></li>
                <li class="nav-link reg"><a href="#Product">Pricing</a></li>
                <li class="nav-link reg"><a href="#Product">About</a></li>
                <li class="nav-link reg"><a href="#Product">Contact Us</a></li>
                <form action="logout" method="get">
                    <button type="submit" class="b">Logout</button>
                </form>
            </ul>
            <img class="img" src="./Hamburger.svg">
        </nav>

        <!-- Media query -->
        <nav class="nav-listd">
            <ul class="listd">
                <li class="nav-link log"><a href="#Product">Home</a></li>
                <li class="nav-link reg"><a href="#Product">Pricing</a></li>
                <li class="nav-link reg"><a href="#Product">About</a></li>
                <li class="nav-link reg"><a href="#Product">Contact Us</a></li>
                <form action="logout" method="get">
                    <button type="submit" class="b">Logout</button>
                </form>
            </ul>
        </nav>
    </div>


    <div class="product-list">
        <h2 class="title">Products</h2>
        <div class="product-cards">
            <% List<Product> productList = (List<Product>) request.getAttribute("productList");
            if (productList != null) {
                for (Product product : productList) { 
                    String base64Image = Base64.getEncoder().encodeToString(product.getUploadImage()); %>
                    <div class="product-card">
                        <h3><%= product.getProductName() %></h3>
                        <p><%= product.getDescription() %></p>
                        <p>Price: $<%= product.getPrice() %></p>
                        <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= product.getProductName() %>">
                    </div>
            <% }
            } else { %>
                <p>No products found.</p>
            <% } %>
        </div>
    </div>

    <div class="footerl-line">
        <p>© 2021 - Present Amazon. All rights reserved.</p>
    </div>
    <script src="Script.js"></script>
</body>
</html>
