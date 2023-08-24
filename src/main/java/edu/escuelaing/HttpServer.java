package edu.escuelaing;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class HttpServer {

    private static HttpConnection httpConnection = new HttpConnection();
    private static HashMap<String, String> cache = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstline = true;
            String path = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(firstline){
                    firstline = false;
                    path = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }

            System.out.println("Path: " + path);

            if (path.split("=").length > 1) {
                outputLine = getHello(path.split("=")[1]);
            } else if (path.startsWith("/movie")) {
                outputLine = "";
            } else {
                outputLine = getDefaultIndex();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String getHello(String movieTitle) throws IOException {
        if (cache.containsKey(movieTitle)) {
            return "HTTP/1.1 200 OK"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + cache.get(movieTitle);
        } else {
            String movieData = httpConnection.getData(movieTitle);
            cache.put(movieTitle, movieData);
            return "HTTP/1.1 200 OK"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + movieData;
        }
    }

    public static String getDefaultIndex() {
        String response = "HTTP/1.1 200 OK"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Consulta de Peliculas</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Consulta de Peliculas</h1>\n" +
                "        <form id=\"movieForm\">\n" +
                "            <label for=\"movieTitle\">Titulo de la Pelicula:</label>\n" +
                "            <input type=\"text\" id=\"movieTitle\" required>\n" +
                "            <button type=\"button\" onclick=\"loadMovieInfo()\">Buscar</button>\n" +
                "        </form>\n" +
                "        <div id=\"movieInfo\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadMovieInfo() {\n" +
                "                const movieTitle = document.getElementById(\"movieTitle\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    if (xhttp.status === 200) {\n" +
                "                        const movieData = JSON.parse(xhttp.responseText);\n" +
                "                        displayMovieInfo(movieData);\n" +
                "                    }\n" +
                "                };\n" +
                "                xhttp.open(\"GET\", \"/movie?title=\" + encodeURIComponent(movieTitle));\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "\n" +
                "            function displayMovieInfo(movieData) {\n" +
                "                const movieInfoDiv = document.getElementById(\"movieInfo\");\n" +
                "                movieInfoDiv.innerHTML = `\n" +
                "                    <h2>${movieData.Title}</h2>\n" +
                "                    <p>Ano: ${movieData.Year}</p>\n" +
                "                    <p>Director: ${movieData.Director}</p>\n" +
                "                    <p>Genero: ${movieData.Genre}</p>\n" +
                "                    <p>Actores: ${movieData.Actors}</p>\n" +
                "                `;\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
        return response;
    }
}
