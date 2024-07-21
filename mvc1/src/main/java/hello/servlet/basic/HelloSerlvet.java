package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloSerlvet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * HttpServletRequest, HttpServletResponse 는 인터페이스이다.
         * WAS 들이 이 인터페이스들을 구현해서 기능을 제공해줌.
         */

        System.out.println("HelloSerlvet.service");

        System.out.println("req = " + request);
        System.out.println("resp = " + response);
        /**
         * [output]
         * req = org.apache.catalina.connector.RequestFacade@68b3a707
         * resp = org.apache.catalina.connector.ResponseFacade@3ecd9e18
         */

        String name = request.getParameter("name");

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + name);
    }
}
