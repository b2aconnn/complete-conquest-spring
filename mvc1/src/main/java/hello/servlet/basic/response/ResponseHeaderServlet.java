package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ResponseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // [ status-line ]
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
//        resp.setHeader("Content-Type", "text/plain");
        resp.setHeader("Cache-Control", "no-cache, not-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");

        redirect(resp);

        PrintWriter writer = resp.getWriter();
        writer.println("안녕하세요");
    }

    private void redirect(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_FOUND);
//        response.setHeader("Location", "/request-body-string");

        response.sendRedirect("/request-body-string");
    }
}
