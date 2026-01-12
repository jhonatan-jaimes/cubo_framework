package com.cubo.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cubo.response.CuboResponse;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboHttpStatus;
import com.cubo.utils.CuboJson;
import com.cubo.utils.CuboPathMethods;

public class CuboServer {

    private HashMap<CuboHttpMethods, List<CuboPathMethods>> routes;

    public CuboServer(){
        this.routes = new HashMap<>();
        for (CuboHttpMethods m : CuboHttpMethods.values()){
            this.routes.put(m, new ArrayList<>());
        }
    }

    public void start(int port) {
        try (ServerSocket sc = new ServerSocket(port)){
            System.out.println("Servidor iniciado en puerto: " + port);
            while (true) {
                Socket client = sc.accept();
                handleClient(client);  
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket client){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream())) {
    
            // 1️⃣ Leer request line
            String requestLine = in.readLine();

            if (requestLine == null) return;
    
            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path   = parts[1];
    
            // 2️⃣ Consumir headers
            String header;
            while ((header = in.readLine()) != null && !header.isEmpty()) {}

            // 3️⃣ Buscar ruta
            CuboResponse cpm = routes.get(CuboHttpMethods.valueOf(method)).stream()
                .filter(e -> e.path().equals(path))
                .findFirst()
                .map(e -> e.func().func(new CuboResponse(), e.req()))
                .get();

            if (cpm != null){
                String status = cpm.getStatus().getValues();
                String body = CuboJson.toJson(cpm.getBody());
                sendResponse(status, body, out);
                return;
            }

            // 4. not_found si no encuentra la ruta.
            sendResponse(CuboHttpStatus.NOT_FOUND.getValues(), "not found", out);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String status, String body, PrintWriter out){
        if (body == " " || body == "" || body == null){
            out.println("HTTP/1.1 " + status);
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println("Content-Length:");
            out.println();
            out.println("");
            out.flush();
        } else {
            out.println("HTTP/1.1 " + status);
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println("Content-Length: " + body.length());
            out.println();
            out.println(body);
            out.flush();
        }
    }

    public void setRoutes(HashMap<CuboHttpMethods, List<CuboPathMethods>> routes){
        for (CuboHttpMethods m : CuboHttpMethods.values()){
            this.routes.get(m).addAll(routes.get(m));
        }
    }

}
